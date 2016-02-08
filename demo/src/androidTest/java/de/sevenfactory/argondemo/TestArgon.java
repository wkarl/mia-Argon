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

package de.sevenfactory.argondemo;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.sevenfactory.argon.Argon;
import de.sevenfactory.argondemo.test.TestActivity;

@RunWith(AndroidJUnit4.class)
public class TestArgon {
    Application mApplication;

    @Rule
    public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

    @Before
    public void setUp() {
        mApplication = mActivityRule.getActivity().getApplication();
    }

    @Test
    public void testRepeatedInit() {
        try {
            Argon.init(mApplication, Config.class, new Config());
        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("Argon initialized twice but no exception thrown.");
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Test
    public void testDebugModeDefaultDisabled() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            Assert.fail("Notification status can only be tested on devices with API level 23 and up.");
        }
        StatusBarNotification[] notifications =
                ((NotificationManager) mApplication.getSystemService(Context.NOTIFICATION_SERVICE))
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
}