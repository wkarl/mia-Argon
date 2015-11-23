package de.prosiebensat1digital.argon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;

import com.jakewharton.processphoenix.ProcessPhoenix;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class ArgonActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);

        getPreferenceManager().setSharedPreferencesName(Argon.ARGON_PREFERENCES);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
        addPreferencesFromResource(getIntent().getIntExtra(Argon.ARGON_PREFERENCES_RESOURCE, -1));
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
