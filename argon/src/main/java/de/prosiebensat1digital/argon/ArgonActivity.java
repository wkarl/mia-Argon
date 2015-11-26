package de.prosiebensat1digital.argon;

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
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);
        mFieldMap = new HashMap<>();
        addPreferencesFromArgon();
    }

    private void addPreferencesFromArgon() {
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);

        mConfig = Argon.getInstance().getConfig();

        Field[] fields = mConfig.getClass().getDeclaredFields();
        for (Field field : fields) {
            addPreferenceFor(mConfig, field, screen);
        }
        setPreferenceScreen(screen);
    }

    private void addPreferenceFor(Object config, Field field, PreferenceScreen screen) {
        if (Modifier.isPublic(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {

            if (field.getType().equals(boolean.class)) {
                try {
                    CheckBoxPreference pref = new CheckBoxPreference(this);
                    pref.setChecked(field.getBoolean(config));
                    pref.setTitle(field.getName());
                    pref.setOnPreferenceChangeListener(this);
                    screen.addPreference(pref);
                    mFieldMap.put(pref, field);
                } catch (IllegalAccessException e) {
                    // Ignore this field
                } catch (IllegalArgumentException f) {
                    // Ignore this field
                }
            }

            if (field.getType().equals(String.class)) {
                try {
                    EditTextPreference pref = new EditTextPreference(this);
                    pref.setText((String) field.get(config));
                    pref.setTitle(field.getName());
                    pref.setOnPreferenceChangeListener(this);
                    screen.addPreference(pref);
                    mFieldMap.put(pref, field);
                } catch (IllegalAccessException e) {
                    // Ignore this field
                } catch (IllegalArgumentException f) {
                    // Ignore this field
                }
            }
            if (field.getType().equals(int.class)) {
                try {
                    EditTextPreference pref = new EditTextPreference(this);
                    pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                    pref.setText(Integer.toString(field.getInt(config)));
                    pref.setTitle(field.getName());
                    pref.setOnPreferenceChangeListener(this);
                    screen.addPreference(pref);
                    mFieldMap.put(pref, field);
                } catch (IllegalAccessException e) {
                    // Ignore this field
                } catch (IllegalArgumentException f) {
                    // Ignore this field
                }
            }
            if (field.getType().equals(long.class)) {
                try {
                    EditTextPreference pref = new EditTextPreference(this);
                    pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                    pref.setText(Float.toString(field.getLong(config)));
                    pref.setTitle(field.getName());
                    pref.setOnPreferenceChangeListener(this);
                    screen.addPreference(pref);
                    mFieldMap.put(pref, field);
                } catch (IllegalAccessException e) {
                    // Ignore this field
                } catch (IllegalArgumentException f) {
                    // Ignore this field
                }
            }
            if (field.getType().equals(float.class)) {
                try {
                    EditTextPreference pref = new EditTextPreference(this);
                    pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                    pref.setText(Float.toString(field.getFloat(config)));
                    pref.setTitle(field.getName());
                    pref.setOnPreferenceChangeListener(this);
                    screen.addPreference(pref);
                    mFieldMap.put(pref, field);
                } catch (IllegalAccessException e) {
                    // Ignore this field
                } catch (IllegalArgumentException f) {
                    // Ignore this field
                }
            }
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
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        try {
            Field field = mFieldMap.get(preference);
            if (preference instanceof EditTextPreference && !field.getType().equals(String.class)) {
                if (field.getType().equals(int.class)) {
                    field.setInt(mConfig, Integer.parseInt((String)newValue));
                } else if (field.getType().equals(float.class)) {
                     field.setFloat(mConfig, Float.parseFloat((String)newValue));
                } else if (field.getType().equals(long.class)) {
                     field.setLong(mConfig, Long.parseLong((String)newValue));
                }
            } else {
                field.set(mConfig, newValue);
            }
            Argon.getInstance().updateConfig(mConfig);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
