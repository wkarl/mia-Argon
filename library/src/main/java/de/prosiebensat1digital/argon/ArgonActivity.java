package de.prosiebensat1digital.argon;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.jakewharton.processphoenix.ProcessPhoenix;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class ArgonActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);
        try {
            this.addPreferencesFromIntent(new Intent(Argon.ARGON_PREFERENCES));
        } catch (NullPointerException e) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.config_hint_title)
                    .setMessage(R.string.config_hint)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProcessPhoenix.triggerRebirth(this);
    }
}
