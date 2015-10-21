package de.prosiebensat1digital.weissbier;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class Weissbier {
    private static final int NOTIFICATION_ID = 666;

    private Context mContext;

    private Weissbier(Context inContext) {
        mContext = inContext;
    }

    /* setup */

    public static Weissbier with(@NonNull Context inContext) {
        return new Weissbier(inContext);
    }

    public Weissbier start() {
        Notification notification = buildNotification();
        getNotificationManager().notify(NOTIFICATION_ID, notification);

        return this;
    }

    public void stop() {
        getNotificationManager().cancel(NOTIFICATION_ID);
    }

    /* helpers */

    private Notification buildNotification() {
        return new NotificationCompat.Builder(mContext)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_debug)
                .setContentTitle("Debug Mode")
                .setContentText("Let's do some debug configuring!")
                .build();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
