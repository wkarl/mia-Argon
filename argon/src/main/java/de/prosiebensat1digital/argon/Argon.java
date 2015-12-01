package de.prosiebensat1digital.argon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

    private ConfigStore mConfigStore;

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
     * @param application
     * @param config
     * @return
     */
    public static <T> Argon init(@NonNull Application application, T config, Class<T> clazz) {
        if (sInstance != null) {
            throw new IllegalStateException("Argon cannot be re-initialised.");
        }
        sInstance = new Argon(application, config, clazz);

        return sInstance;
    }

    private <T> Argon(@NonNull Context inContext, T config, Class<T> clazz) {
        mContext = inContext;
        mConfigStore = new ConfigStore<>(inContext, config, clazz);
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
    public Argon start() {
        Notification notification = buildNotification();
        getNotificationManager().notify(NOTIFICATION_ID, notification);
        return this;
    }

    public void stop() {
        getNotificationManager().cancel(NOTIFICATION_ID);
    }

    /* Notification helpers */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Notification buildNotification() {

        String title = mContext.getString(mTitleRes);
        String text = mContext.getString(mTextRes);

        int color = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? mContext.getColor(mColorRes)
                : mContext.getResources().getColor(mColorRes);

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
        //noinspection unchecked
        mConfigStore.update(config);
    }

    public <T> T getConfig() {
        //noinspection unchecked
        return (T) mConfigStore.getConfig();
    }

    public void restartProcess() {
        ProcessPhoenix.triggerRebirth(mContext);
    }
}
