/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 ProSiebenSat.1 Digital GmbH
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.sevenfactory.argon;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListAdapter;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.sevenfactory.argontest.Config;
import de.sevenfactory.argontest.TestActivity;

@RunWith(AndroidJUnit4.class)
public class TestArgon {
    private static Application sApplication;

    @ClassRule
    public static ActivityTestRule<TestActivity> sActivityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Rule
    public ActivityTestRule<ArgonActivity> mArgonActivityTestRule = new ActivityTestRule<>(ArgonActivity.class);
    private ArgonActivity mArgonActivity;

    @BeforeClass
    public static void initArgon() {
        sApplication = sActivityTestRule.getActivity().getApplication();
        String json = "{\n" +
                "\"showHeadline\": true,\n" +
                "\"text\": \"test\",\n" +
                "\"intValue\": 20,\n" +
                "\"longValue\": 200000000000000000,\n" +
                "\"floatValue\": 0.5823,\n" +
                "\"listValue\": \"Option 1\"\n," +
                "\"ignoredValue\": \"This should be ignored.\"\n," +
                "\"list\": [\"one\", \"two\", \"three\"]\n" +
                "}";

        Config defaultConfig = new Gson().fromJson(json, Config.class);

        // MockStore configuration is immutable, no need to re-init
        MockStore store = new MockStore(defaultConfig);

        Argon.init(sApplication, store);
    }

    /* Argon tests */

    @Test
    public void testRepeatedInit() {
        try {
            Argon.init(sApplication, Config.class, new Config());
        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("Argon initialized twice but no exception thrown.");
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Test
    public void testDebugModeDefaultDisabled() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Assert.fail("Notification status can only be tested on devices with API level 23 and up.");
        }
        StatusBarNotification[] notifications =
                ((NotificationManager) sApplication.getSystemService(Context.NOTIFICATION_SERVICE))
                        .getActiveNotifications();

        for (StatusBarNotification notification : notifications) {
            Assert.assertNotEquals(getClass().getPackage().getName(), notification.getPackageName());
        }
    }

    @Test
    public void testUpdateConfigInvalid() {
        try {
            Argon.updateConfig("test");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail("Config updated with wrong type but no exception thrown.");
    }

    @Test
    public void testUpdateNull() {
        Config config = new Config();
        config.text = "test";

        Argon.updateConfig(config);

        try {
            Argon.updateConfig(null);
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail("Argon initialized with null value but no exception thrown.");
    }

    @Test
    public void testListType() {
        Config config = Argon.getConfig();
        Assert.assertEquals(3, config.list.size());
    }

    @Before
    public void setUp() {
        mArgonActivity = mArgonActivityTestRule.getActivity();
    }

    /* ArgonActivity tests */

    @Test
    public void testIgnoredFields() {
        ListAdapter adapter = mArgonActivity.getListView().getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            // Explicitly ignored
            Assert.assertNotEquals("ignored", adapter.getItem(i).toString());

            // Implicitly ignored (only primitive type and String options can be displayed)
            Assert.assertNotEquals("list", adapter.getItem(i).toString());
        }
    }

    @Test
    public void testNameAnnotation() {
        ListAdapter adapter = mArgonActivity.getListView().getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if ("Text property".equals(adapter.getItem(i).toString())) {
                return;
            }
        }
        Assert.fail("Annotated preference not found: \"Text property\"");
    }
}