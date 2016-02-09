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

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestArgon {

    @Rule
    public ActivityTestRule<ArgonActivity> mArgonActivityTestRule = new ActivityTestRule<>(ArgonActivity.class);
    private ArgonActivity mArgonActivity;

    /* Argon tests */

    @Test
    public void testRepeatedInit() {
        try {
            Argon.init(mArgonActivity.getApplication(), Config.class, new Config());
        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("Argon initialized twice but no exception thrown.");
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