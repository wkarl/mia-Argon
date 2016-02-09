package de.sevenfactory.argon;

import android.app.Application;

import com.google.gson.Gson;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String json = "{\n" +
                "\"showHeadline\": true,\n" +
                "\"text\": \"test\",\n" +
                "\"intValue\": 20,\n" +
                "\"longValue\": 200000000000000000,\n" +
                "\"floatValue\": 0.5823,\n" +
                "\"listValue\": \"Option 1\"\n," +
                "\"ignoredValue\": \"This should be ignored.\"\n," +
                "\"list\": [\"one\", \"two\", \"three\"]\n" +
                "}";

        Config defaultConfig = new Gson().fromJson(json, Config.class);

        // MockStore configuration is immutable, no need to re-init
        MockStore store = new MockStore(defaultConfig);

        Argon.init(this, store);
    }
}
