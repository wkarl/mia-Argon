package de.prosiebensat1digital.argon;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jakewharton.processphoenix.ProcessPhoenix;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class ArgonActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);
        addPreferencesFromResource(getIntent().getIntExtra(Argon.ARGON_PREFERENCES_RESOURCE, -1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProcessPhoenix.triggerRebirth(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish(); // There is only one menu item, no need to distinguish
        return true;
    }
}
