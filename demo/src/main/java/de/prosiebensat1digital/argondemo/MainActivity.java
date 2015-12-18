package de.prosiebensat1digital.argondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.prosiebensat1digital.argon.Argon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ConfigModel mConfig;
    
    private TextView mHeadlineView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get config from Argon
        mConfig = Argon.getInstance().getConfig();
        
        // Find views
        mHeadlineView = (TextView) findViewById(R.id.headline);
        
        TextView stringView   = ((TextView) findViewById(R.id.stringText));
        TextView intView      = ((TextView) findViewById(R.id.integerText));
        TextView floatView    = ((TextView) findViewById(R.id.floatText));
        TextView longView     = ((TextView) findViewById(R.id.longText));
        Button   toggleButton = (Button) findViewById(R.id.toggleButton);
        
        mHeadlineView.setText(mConfig.showHeadline ? "Hello World!" : null);
        
        stringView.setText(mConfig.text);
        intView.setText(String.valueOf(mConfig.intValue));
        floatView.setText(String.valueOf(mConfig.floatValue));
        longView.setText(String.valueOf(mConfig.longValue));
        toggleButton.setOnClickListener(this);
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
    
    @Override
    public void onClick(View v) {
        mConfig.showHeadline = TextUtils.isEmpty(mHeadlineView.getText());
        
        Argon.getInstance().updateConfig(mConfig);
        Argon.getInstance().restartProcess();
    }
}
