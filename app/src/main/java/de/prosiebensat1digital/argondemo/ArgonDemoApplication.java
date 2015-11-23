package de.prosiebensat1digital.argondemo;

import android.app.Application;

import de.prosiebensat1digital.argon.Argon;

public class ArgonDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    
        Argon.init(this, R.xml.preferences, R.drawable.ic_debug, R.string.app_name);
    }
}
