package de.prosiebensat1digital.weissbierdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.prosiebensat1digital.weissbier.Weissbier;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Weissbier.with(this).start();
    }

    @Override
    protected void onStop() {
        Weissbier.with(this).stop();

        super.onStop();
    }
}
