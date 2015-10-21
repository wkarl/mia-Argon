package de.prosiebensat1digital.weissbier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class Weissbier {
    private static final int NOTIFICATION_ID = 666;
    private static final int REQUEST_CODE    = 0;
    
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

    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(mContext)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_debug)
                .setContentTitle("Debug Mode")
                .setContentText("Let's do some debug configuring!")
                .setContentIntent(buildContentIntent())
                .build();
    }

    private PendingIntent buildContentIntent(){
        Intent intent = new Intent(mContext, WeissbierActivity.class);

        return PendingIntent.getActivity(mContext, REQUEST_CODE, intent, 0);
    }
}
