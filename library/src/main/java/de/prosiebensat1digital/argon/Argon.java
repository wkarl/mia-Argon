package de.prosiebensat1digital.argon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class Argon {
    static final String ARGON_PREFERENCES_RESOURCE = "de.prosiebensat1digital.argon.PREFERENCES_RESOURCE";
    private static final int NOTIFICATION_ID = 666;
    private static final int REQUEST_CODE    = 0;

    private static Argon sInstance;

    private Context mContext;
    private int mPreferenceResourceId;

    private Argon(Context inContext, int preferenceResourceId) {
        mContext = inContext;
        mPreferenceResourceId = preferenceResourceId;
    }

    /* setup */

    public static void init(@NonNull Application application, int preferenceResourceId) {
        sInstance = new Argon(application, preferenceResourceId);
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
                .setSmallIcon(R.drawable.ic_debug)
                .setContentTitle(mContext.getString(R.string.notification_title))
                .setContentText(mContext.getString(R.string.notification_text))
                .setContentIntent(buildContentIntent())
                .build();
    }

    private PendingIntent buildContentIntent(){
        Intent intent = new Intent(mContext, ArgonActivity.class);
        intent.putExtra(ARGON_PREFERENCES_RESOURCE, mPreferenceResourceId);
        intent.setAction("ArgonActivity");
        return PendingIntent.getActivity(mContext, REQUEST_CODE, intent, 0);
    }

    public String getString(String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getInt(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getFloat(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getLong(key, defaultValue);
    }
}
