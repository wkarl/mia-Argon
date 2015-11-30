package de.prosiebensat1digital.argondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import de.prosiebensat1digital.argon.Argon;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    ConfigModel mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.headline);
        mConfig = Argon.getInstance().getConfig();
        mTextView.setText(mConfig.showHeadline ? "Hello World!" : null);
        ((TextView) findViewById(R.id.text)).setText(mConfig.text);
        ((TextView) findViewById(R.id.integerValue)).setText(Integer.toString(mConfig.intValue));
        ((TextView) findViewById(R.id.floatValue)).setText(Float.toString(mConfig.floatValue));
        ((TextView) findViewById(R.id.longValue)).setText(Long.toString(mConfig.longValue));
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

    public void toggleHeadline(View view) {
        mConfig.showHeadline = TextUtils.isEmpty(mTextView.getText());
        Argon.getInstance().updateConfig(mConfig);
        Argon.getInstance().restartProcess();
    }
}
