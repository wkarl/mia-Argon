package de.prosiebensat1digital.argon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.support.v4.app.NotificationCompat;

import java.util.Map;

public class Argon {
    static final String ARGON_PREFERENCES_RESOURCE = "de.prosiebensat1digital.argon.PREFERENCES_RESOURCE";
    static final String ARGON_PREFERENCES          = "de.prosiebensat1digital.argon.PREFERENCES";
    
    private static final int NOTIFICATION_ID = 666;
    private static final int REQUEST_CODE    = 0;
    
    private static Argon sInstance;
    
    private Context mContext;
    
    @XmlRes
    private       int mPreferenceResourceId;
    @DrawableRes
    private final int mNotificationIconRes;
    @StringRes
    private final int mTitleRes;
    @ColorRes
    private final int mColorRes;
    
    private Argon(@NonNull Context inContext, @XmlRes int preferenceResourceId,
                  @DrawableRes int notificationIconRes, @StringRes int titleRes,
                  @ColorRes int colorRes) {
        mContext = inContext;
        mPreferenceResourceId = preferenceResourceId;
        mTitleRes = titleRes;
        mNotificationIconRes = notificationIconRes;
        mColorRes = colorRes;
    }
    
    /* setup */
    public static void init(@NonNull Application application, @XmlRes int preferenceResourceId,
                            @DrawableRes int notificationIconRes, @StringRes int titleRes,
                            @ColorRes int colorRes) {
        sInstance = new Argon(application, preferenceResourceId, notificationIconRes, titleRes, colorRes);
    }
    
    public static Argon getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Please set up Argon in your Application class using Argon.init(Application, int).");
        }
        return sInstance;
    }
    
    public Argon start() {
        Notification notification = buildNotification();
        getNotificationManager().notify(NOTIFICATION_ID, notification);
        return this;
    }
    
    public void stop() {
        getNotificationManager().cancel(NOTIFICATION_ID);
    }

    /* helpers */
    
    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    
    private Notification buildNotification() {
        return new NotificationCompat.Builder(mContext)
                .setOngoing(true)
                .setSmallIcon(mNotificationIconRes)
                .setContentTitle(mContext.getString(mTitleRes))
                .setContentText(mContext.getString(R.string.notification_text))
                .setColor(mContext.getColor(mColorRes))
                .setContentIntent(buildContentIntent())
                .build();
    }
    
    private PendingIntent buildContentIntent() {
        Intent intent = new Intent(mContext, ArgonActivity.class);
        intent.putExtra(ARGON_PREFERENCES_RESOURCE, mPreferenceResourceId);
        intent.setAction("ArgonActivity");
        return PendingIntent.getActivity(mContext, REQUEST_CODE, intent, 0);
    }
    
    public String getString(String key, String defaultValue) {
        return mContext.getSharedPreferences(ARGON_PREFERENCES, Context.MODE_PRIVATE).getString(key, defaultValue);
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        return mContext.getSharedPreferences(ARGON_PREFERENCES, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }
    
    public int getInt(String key, int defaultValue) {
        return mContext.getSharedPreferences(ARGON_PREFERENCES, Context.MODE_PRIVATE).getInt(key, defaultValue);
    }
    
    public float getFloat(String key, float defaultValue) {
        return mContext.getSharedPreferences(ARGON_PREFERENCES, Context.MODE_PRIVATE).getFloat(key, defaultValue);
    }
    
    public long getLong(String key, long defaultValue) {
        return mContext.getSharedPreferences(ARGON_PREFERENCES, Context.MODE_PRIVATE).getLong(key, defaultValue);
    }
    
    public void setFeatureFlags(Map<String, Boolean> featureFlags) {
        Intent intent      = new Intent(mContext, ArgonActivity.class);
        Bundle flagsBundle = new Bundle();
        
        for (Map.Entry<String, Boolean> entry : featureFlags.entrySet()) {
            flagsBundle.putBoolean(entry.getKey(), entry.getValue());
        }
        intent.putExtra(ArgonActivity.FEATURE_FLAGS, flagsBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ARGON_PREFERENCES_RESOURCE, mPreferenceResourceId);
        mContext.startActivity(intent);
    }
}
