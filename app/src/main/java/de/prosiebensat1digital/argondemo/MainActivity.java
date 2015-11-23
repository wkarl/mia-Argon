package de.prosiebensat1digital.argondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import de.prosiebensat1digital.argon.Argon;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setText(Argon.getInstance().getBoolean("text", true) ? "Hello World!" : null);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Argon.getInstance().start();
    }

    @Override
    protected void onStop() {
        Argon.getInstance().stop();

        super.onStop();
    }
}
