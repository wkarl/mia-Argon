package de.prosiebensat1digital.argondemo;

import android.app.Application;

import de.prosiebensat1digital.argon.Argon;

public class ArgonDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    
        Argon.init(this, R.xml.preferences)
                .setIcon(R.drawable.ic_debug)
                .setTitle(R.string.notification_title)
                .setText(R.string.notification_text)
                .setColor(R.color.colorAccent);
    }
}
