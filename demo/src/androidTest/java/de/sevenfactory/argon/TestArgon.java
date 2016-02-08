package de.sevenfactory.argon;

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.sevenfactory.argondemo.Config;
import de.sevenfactory.argondemo.MainActivity;

@RunWith(AndroidJUnit4.class)
public class TestArgon {
    Application mApplication;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

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
        Assert.fail("Argon initialized twice without an exception.");
    }
}