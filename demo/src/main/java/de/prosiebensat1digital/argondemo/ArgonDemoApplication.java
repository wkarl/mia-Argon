package de.prosiebensat1digital.argondemo;

import android.app.Application;

import com.google.gson.Gson;

import de.prosiebensat1digital.argon.Argon;

public class ArgonDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String json = "{\n" +
                "\"showHeadline\": true,\n" +
                "\"text\": test,\n" +
                "\"intValue\": 20,\n" +
                "\"longValue\": 200000000000000000,\n" +
                "\"floatValue\": 0.5823\n" +
                "}";
        Gson gson = new Gson();
        ConfigModel config = gson.fromJson(json, ConfigModel.class);
        Argon.init(this, config, ConfigModel.class)
                .setIcon(R.drawable.ic_debug)
                .setTitle(R.string.notification_title)
                .setText(R.string.notification_text)
                .setColor(R.color.colorAccent);
    }
}
