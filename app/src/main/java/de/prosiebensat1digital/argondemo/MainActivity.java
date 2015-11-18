package de.prosiebensat1digital.argondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.prosiebensat1digital.argon.Argon;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
