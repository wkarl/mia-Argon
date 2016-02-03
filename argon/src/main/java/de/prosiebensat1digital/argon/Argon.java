package de.prosiebensat1digital.argon;

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
    public static Argon getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Please set up Argon in your Application class using Argon.init(Application, int).");
        }
        return sInstance;
    }

    /**
     * Init Argon with a fallback configuration.
     */
    public static <T> Argon init(@NonNull Application application, Class<T> tClass, T defaultConfig) {
        if (sInstance != null) {
            throw new IllegalStateException("Argon cannot be re-initialised.");
        }
        sInstance = new Argon(application, tClass, defaultConfig);

        return sInstance;
    }

    public <T> Argon(@NonNull Application application, Class<T> tClass, T defaultConfig) {
        mContext = application;
        mConfigStore = new ConfigStore(application, tClass, defaultConfig);
        application.registerActivityLifecycleCallbacks(new ArgonActivityLifecycleCallbacks());
    }

    /* Builder-like pattern */
    public Argon setIcon(@DrawableRes int iconRes) {
        mIconRes = iconRes;
        return this;
    }

    public Argon setTitle(@StringRes int titleRes) {
        mTitleRes = titleRes;
        return this;
    }

    public Argon setText(@StringRes int textRes) {
        mTextRes = textRes;
        return this;
    }

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

    public <T> void updateConfig(T config) {
        mConfigStore.update(config);
    }

    public <T> T getConfig() {
        return mConfigStore.getConfig();
    }

    public void restartProcess() {
        ProcessPhoenix.triggerRebirth(mContext);
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
