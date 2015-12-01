package de.prosiebensat1digital.argon;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ConfigStore<T> {
    private static final String PREFERENCE_FILE = "de.prosiebensat1digital.argon.PREFERENCES";
    private static final String JSON_PREFERENCE = "JSON_CONFIG";

    private final SharedPreferences mPreferences;
    private T mConfig;

    ConfigStore(Context context, T initialConfig, Class<T> typeParameterClass) {
        mPreferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        String savedJson = mPreferences.getString(JSON_PREFERENCE, null);
        T savedConfig = savedJson == null ? null : fromJson(savedJson, typeParameterClass);

        if (savedConfig == null) {
            update(initialConfig);
            savedConfig = initialConfig;
        }

        mConfig = savedConfig;
    }

    void update(T config) {
        Gson gson = new Gson();
        mPreferences.edit().putString(JSON_PREFERENCE, gson.toJson(config)).apply();

    }

    private T fromJson(String json, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            // Use default config instead
            return null;
        }
    }

    T getConfig() {
        return mConfig;
    }
}