package de.prosiebensat1digital.weissbier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Thomas Mann on 21/10/15.
 */
public class WeissbierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DefaultPreferencesFragment())
                .commit();
    }
}
