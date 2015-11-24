package de.prosiebensat1digital.argon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jakewharton.processphoenix.ProcessPhoenix;

public class ArgonActivity extends PreferenceActivity {
    static final String FEATURE_FLAGS = "feature_flags";

    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);

        getPreferenceManager().setSharedPreferencesName(Argon.ARGON_PREFERENCES);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
        addPreferencesFromResource(getIntent().getIntExtra(Argon.ARGON_PREFERENCES_RESOURCE, -1));

        Bundle featureFlags = getIntent().getBundleExtra(FEATURE_FLAGS);
        if (inBundle == null && featureFlags != null) {
            SharedPreferences prefs = getSharedPreferences(Argon.ARGON_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            for (String flag : featureFlags.keySet()) {
                if (prefs.contains(flag)) {
                    editor.putBoolean(flag, featureFlags.getBoolean(flag));
                }
            }
            editor.apply();
            ProcessPhoenix.triggerRebirth(this);
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
}
