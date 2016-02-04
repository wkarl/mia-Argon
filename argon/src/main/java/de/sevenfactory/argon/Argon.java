/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 ProSiebenSat.1 Digital GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.sevenfactory.argon;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;

import com.jakewharton.processphoenix.ProcessPhoenix;

/**
 * Argon maintains a singleton instance that holds, persists and updates a user defined
 * configuration object of any type.
 * <p/>
 * A notification is shown while the app is running in the foreground. This notification opens a
 * configuration activity containing all valid fields of your configuration object. Valid fields are
 * Strings and the primitive types int, float and long. Field names displayed in the configuration
 * activity can be changed using the annotation {@link de.sevenfactory.argon.Name}.
 * <p/>
 * Leaving the configuration activity always triggers a process restart after which the updated
 * configuration object is available.
 * For consistency reasons, configuration updates during runtime using {@link #updateConfig(Object)}
 * will only be effective after a process restart.
 */
public class Argon {
    private static final int NOTIFICATION_ID = 666;
    private static final int REQUEST_CODE = 0;

    private static Argon sInstance;

    private final Context mContext;
    private final ConfigStore mConfigStore;

    @DrawableRes
    private int mIconRes = R.drawable.ic_bug_report_white_24dp;
    @StringRes
    private int mTitleRes = R.string.app_name;
    @StringRes
    private int mTextRes = R.string.text;
    @ColorRes
    private int mColorRes = R.color.colorPrimary;

    /* Singleton methods */
    private static Argon getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Please set up Argon in your Application class using Argon.init(Application, int).");
        }
        return sInstance;
    }

    /**
     * Init Argon with a fallback configuration. This has to be done in {@link Application#onCreate()} to
     * guarantee correct interpretation of lifecycle events.
     *
     * @param application your application class
     * @param tClass the class of your configuration model Object
     * @param defaultConfig an instance of your configuration
     * @param <T> the Type of your configuration Object
     * @return the newly created Argon instance that can be used to chain setters
     * @throws IllegalStateException if Argon has already been initialized
     */
    public static <T> Argon init(@NonNull Application application, Class<T> tClass, T defaultConfig) {
        if (sInstance != null) {
            throw new IllegalStateException("Argon cannot be re-initialised.");
        }
        sInstance = new Argon(application, tClass, defaultConfig);

        return sInstance;
    }

    private <T> Argon(@NonNull Application application, Class<T> tClass, T defaultConfig) {
        mContext = application;
        mConfigStore = new ConfigStore(application, tClass, defaultConfig);
        application.registerActivityLifecycleCallbacks(new ArgonActivityLifecycleCallbacks());
    }

    /* Builder-like pattern */

    /**
     * Sets the drawable used for the notification icon.
     * @param iconRes the icon's drawable resource
     * @return the singleton instance of Argon to be used for chaining
     */
    public Argon setIcon(@DrawableRes int iconRes) {
        mIconRes = iconRes;
        return this;
    }

    /**
     * Sets the notification's title.
     * @param titleRes the title string resource
     * @return the singleton instance of Argon to be used for chaining
     */
    public Argon setTitle(@StringRes int titleRes) {
        mTitleRes = titleRes;
        return this;
    }

    /**
     * Sets the notification's text.
     * @param textRes the text string resource
     * @return the singleton instance of Argon to be used for chaining
     */
    public Argon setText(@StringRes int textRes) {
        mTextRes = textRes;
        return this;
    }

    /**
     * Sets the notification's color.
     * @param colorRes the color resource
     * @return the singleton instance of Argon to be used for chaining
     */
    public Argon setColor(@ColorRes int colorRes) {
        mColorRes = colorRes;
        return this;
    }

    /* Lifecycle */
    private void start() {
        Notification notification = buildNotification();
        getNotificationManager().notify(NOTIFICATION_ID, notification);
    }

    private void stop() {
        getNotificationManager().cancel(NOTIFICATION_ID);
    }

    /* Notification helpers */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @SuppressWarnings("deprecation")
    private Notification buildNotification() {
        String title = mContext.getString(mTitleRes);
        String text = mContext.getString(mTextRes);

        int color;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = mContext.getColor(mColorRes);
        } else {
            color = mContext.getResources().getColor(mColorRes);
        }

        return new NotificationCompat.Builder(mContext)
                .setOngoing(true)
                .setSmallIcon(mIconRes)
                .setContentTitle(title)
                .setContentText(text)
                .setColor(color)
                .setContentIntent(buildContentIntent())
                .build();
    }

    private PendingIntent buildContentIntent() {
        Intent intent = new Intent(mContext, ArgonActivity.class);
        return PendingIntent.getActivity(mContext, REQUEST_CODE, intent, 0);
    }

    /**
     * Updates the singleton instance's configuration object. Changes will not be visible until
     * the process is restarted to maintain consistency across your application.
     * @param config the modified configuration object
     * @param <T> the type of the configuration object
     */
    public static <T> void updateConfig(T config) {
        getInstance().mConfigStore.update(config);
    }

    /**
     * Getter for the configuration object. Please not that this will not return an updated object
     * when called after {@link #updateConfig(Object)} until the process is restarted.
     * @param <T> the type of the configuration object
     * @return the current configuration object
     */
    public static <T> T getConfig() {
        return getInstance().mConfigStore.getConfig();
    }

    /**
     * Convenience method to restart the app process. This method is used to force a
     * configuration update.
     */
    public static void restartProcess() {
        ProcessPhoenix.triggerRebirth(getInstance().mContext);
    }

    private class ArgonActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private int mStarted;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStarted(Activity inActivity) {
            if (mStarted == 0) {
                start();
            }
            mStarted++;
        }

        @Override
        public void onActivityStopped(Activity inActivity) {
            mStarted--;
            if (mStarted <= 0) {
                stop();
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
