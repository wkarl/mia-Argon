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
}