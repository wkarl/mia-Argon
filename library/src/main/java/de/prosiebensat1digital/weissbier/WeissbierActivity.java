package de.prosiebensat1digital.weissbier;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class WeissbierActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);

        addPreferencesFromResource(R.xml.preferences);
    }
}
