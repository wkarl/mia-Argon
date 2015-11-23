package de.prosiebensat1digital.argondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

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

    public void toggleText(View view) {
        Map<String, Boolean> flags = new HashMap<>();
        flags.put("text", TextUtils.isEmpty(mTextView.getText()));
        Argon.getInstance().setFeatureFlags(flags);
    }
}
