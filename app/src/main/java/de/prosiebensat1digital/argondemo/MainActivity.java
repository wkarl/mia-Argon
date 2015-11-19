package de.prosiebensat1digital.argondemo;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import de.prosiebensat1digital.argon.Argon;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("text", true) ? "Hello World!" : null);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Argon.with(this).start();
    }

    @Override
    protected void onStop() {
        Argon.with(this).stop();

        super.onStop();
    }
}
