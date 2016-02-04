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

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jakewharton.processphoenix.ProcessPhoenix;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ArgonActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private Object                 mConfig;
    private Map<Preference, Field> mFieldMap;
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        mFieldMap = new HashMap<>();
        
        try {
            addPreferencesFromArgon();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("deprecation")
    private void addPreferencesFromArgon() throws IllegalAccessException {
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);
        
        mConfig = Argon.getConfig();
        
        Field[] fields = mConfig.getClass().getDeclaredFields();
        for (Field field : fields) {
            addPreferenceFor(mConfig, field, screen);
        }
        setPreferenceScreen(screen);
    }

    private void addPreferenceFor(Object config, Field field, PreferenceScreen screen) throws IllegalAccessException {
        if (Modifier.isPublic(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
            String name = getAnnotatedName(field);

            if (field.getType().equals(boolean.class)) {
                CheckBoxPreference pref = new CheckBoxPreference(this);
                pref.setChecked(field.getBoolean(config));
                pref.setTitle(name);
                pref.setOnPreferenceChangeListener(this);
                screen.addPreference(pref);
                mFieldMap.put(pref, field);
            }
            
            if (field.getType().equals(String.class)) {
                EditTextPreference pref = new EditTextPreference(this);
                pref.setText((String) field.get(config));
                pref.setTitle(name);
                pref.setOnPreferenceChangeListener(this);
                screen.addPreference(pref);
                mFieldMap.put(pref, field);
            }
            
            if (field.getType().equals(int.class)) {
                EditTextPreference pref = new EditTextPreference(this);
                pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                pref.setText(Integer.toString(field.getInt(config)));
                pref.setTitle(name);
                pref.setOnPreferenceChangeListener(this);
                screen.addPreference(pref);
                mFieldMap.put(pref, field);
            }
            
            if (field.getType().equals(long.class)) {
                EditTextPreference pref = new EditTextPreference(this);
                pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                pref.setText(Long.toString(field.getLong(config)));
                pref.setTitle(name);
                pref.setOnPreferenceChangeListener(this);
                screen.addPreference(pref);
                mFieldMap.put(pref, field);
            }
            
            if (field.getType().equals(float.class)) {
                EditTextPreference pref = new EditTextPreference(this);
                pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                pref.setText(Float.toString(field.getFloat(config)));
                pref.setTitle(name);
                pref.setOnPreferenceChangeListener(this);
                screen.addPreference(pref);
                mFieldMap.put(pref, field);
            }
        }
    }

    private String getAnnotatedName(Field field) {
        Name annotation = field.getAnnotation(Name.class);

        if (annotation != null) {
            return annotation.value();
        } else {
            return field.getName();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // There is only one menu item, no need to distinguish
        ProcessPhoenix.triggerRebirth(this);
        return true;
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProcessPhoenix.triggerRebirth(this);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        try {
            Field field = mFieldMap.get(preference);
            if (preference instanceof EditTextPreference && !field.getType().equals(String.class)) {
                if (field.getType().equals(int.class)) {
                    field.setInt(mConfig, Integer.parseInt((String) newValue));
                } else if (field.getType().equals(float.class)) {
                    field.setFloat(mConfig, Float.parseFloat((String) newValue));
                } else if (field.getType().equals(long.class)) {
                    field.setLong(mConfig, Long.parseLong((String) newValue));
                }
            } else {
                field.set(mConfig, newValue);
            }
            Argon.updateConfig(mConfig);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
