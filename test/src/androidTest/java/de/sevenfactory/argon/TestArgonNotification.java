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
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@TargetApi(Build.VERSION_CODES.M)
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
@RunWith(AndroidJUnit4.class)
public class TestArgonNotification {
    private TestActivity mTestActivity;

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Before
    public void setUp() {
        mTestActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testDebugModeDefaultDisabled() {
        StatusBarNotification[] notifications =
                ((NotificationManager) mTestActivity.getApplication().getSystemService(Context.NOTIFICATION_SERVICE))
                        .getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            Assert.assertNotEquals(mTestActivity.getApplication().getPackageName(), notification.getPackageName());
        }
    }

    @Test
    public void testEnableDebugMode() {
        Argon.setDebugModeEnabled(true);
        StatusBarNotification[] notifications =
                ((NotificationManager) mTestActivity.getApplication().getSystemService(Context.NOTIFICATION_SERVICE))
                        .getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            if (mTestActivity.getApplication().getPackageName().equals(notification.getPackageName())) {
                return;
            }
        }
        Assert.fail("Debug mode activated but no notification shown");
    }
}