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

package de.sevenfactory.argondemo;

import android.app.Application;

import com.google.gson.Gson;

import de.sevenfactory.argon.Argon;

public class ArgonDemoApplication extends Application {
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
                "\"ignoredValue\": \"This should be ignored.\"\n" +
                "}";

        Config defaultConfig = new Gson().fromJson(json, Config.class);

        Argon.init(this, Config.class, defaultConfig)
                .setIcon(R.drawable.ic_debug)
                .setTitle(R.string.notification_title)
                .setText(R.string.notification_text)
                .setColor(R.color.colorAccent);

        // Explicitly enable debug mode (disabled by default)
        Argon.setDebugModeEnabled(true);
    }
}
