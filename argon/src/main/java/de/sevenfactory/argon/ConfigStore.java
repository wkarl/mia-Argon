/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 ProSiebenSat.1 Digital GmbH
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.sevenfactory.argon;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

class ConfigStore {
    private static final String PREFERENCE_FILE = "de.sevenfactory.argon.PREFERENCES";
    private static final String JSON_PREFERENCE = "JSON_CONFIG";
    
    private final SharedPreferences mPreferences;
    private       Object            mConfig;
    
    <T> ConfigStore(Context context, Class<T> tClass, T defaultConfig) {
        mPreferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        String savedJson   = mPreferences.getString(JSON_PREFERENCE, null);
        T      savedConfig = savedJson == null ? null : fromJson(savedJson, tClass);
        
        if (savedConfig == null) {
            update(defaultConfig);
            savedConfig = defaultConfig;
        }
        
        mConfig = savedConfig;
    }
    
    <T> void update(T config) {
        if (mConfig == null || config.getClass().equals(mConfig.getClass())) {
            Gson gson = new Gson();
            mPreferences.edit().putString(JSON_PREFERENCE, gson.toJson(config)).apply();
        } else {
            throw new IllegalArgumentException("Expected: " + mConfig.getClass().getName()
                    + ", actual: " + config.getClass().getName());
        }
    }
    
    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            // Use default config instead
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    <T> T getConfig() {
        return (T) mConfig;
    }
}