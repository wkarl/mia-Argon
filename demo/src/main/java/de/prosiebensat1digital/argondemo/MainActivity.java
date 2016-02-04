package de.prosiebensat1digital.argondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import de.prosiebensat1digital.argon.Argon;

public class MainActivity extends AppCompatActivity {
    private ConfigModel mConfig;
    
    private TextView mHeadlineView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get config from Argon
        mConfig = Argon.getConfig();
        
        // Find views
        mHeadlineView = (TextView) findViewById(R.id.headline);
        
        mHeadlineView.setText(mConfig.showHeadline ? "Hello World!" : null);
    }

    public void toggleHeadline(View v) {
        mConfig.showHeadline = TextUtils.isEmpty(mHeadlineView.getText());

        Argon.updateConfig(mConfig);
        Argon.restartProcess();
    }

    public void goToDetails(View v) {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
}
