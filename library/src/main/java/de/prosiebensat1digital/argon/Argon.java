package de.prosiebensat1digital.argon;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.jakewharton.processphoenix.ProcessPhoenix;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class Argon {
    static final String ARGON_PREFERENCES = "de.prosiebensat1digital.argon.PREFERENCES";
    static final String ARGON_RESTART_FLAG = "de.prosiebensat1digital.argon.RESTART_FLAG";
    private static final int NOTIFICATION_ID = 666;
    private static final int REQUEST_CODE    = 0;

    private Context mContext;

    private Argon(Context inContext) {
        mContext = inContext;
    }

    /* setup */

    public static Argon with(@NonNull Context inContext) {
        return new Argon(inContext);
    }

    public Argon start() {
        Notification notification = buildNotification();
        getNotificationManager().notify(NOTIFICATION_ID, notification);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (prefs.getBoolean(ARGON_RESTART_FLAG, false)) {
            prefs.edit().putBoolean(ARGON_RESTART_FLAG, false).apply();
            ProcessPhoenix.triggerRebirth(mContext);
        }
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
