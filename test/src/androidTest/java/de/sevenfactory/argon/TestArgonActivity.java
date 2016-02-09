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

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListAdapter;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.sevenfactory.argontest.Config;

@RunWith(AndroidJUnit4.class)
public class TestArgonActivity {
    private static Application sApplication;

    @ClassRule
    public static ActivityTestRule<ArgonActivity> sActivityTestRule = new ActivityTestRule<>(ArgonActivity.class);

    @BeforeClass
    public static void setUp() {
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

    @Test
    public void testIgnoredFields() {
        ListAdapter adapter = sActivityTestRule.getActivity().getListView().getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            // Explicitly ignored
            Assert.assertNotEquals("ignored", adapter.getItem(i).toString());

            // Implicitly ignored (only primitive type and String options can be displayed)
            Assert.assertNotEquals("list", adapter.getItem(i).toString());
        }
    }

    @Test
    public void testNameAnnotation() {
        ListAdapter adapter = sActivityTestRule.getActivity().getListView().getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if ("Text property".equals(adapter.getItem(i).toString())) {
                return;
            }
        }
        Assert.fail("Annotated preference not found: \"Text property\"");
    }
}