package de.sevenfactory.argondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import de.sevenfactory.argon.Argon;

public class DetailsActivity extends AppCompatActivity {
    private ConfigModel mConfig;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        
        // Get config from Argon
        mConfig = Argon.getConfig();
        
        // Find views
        
        TextView stringView   = ((TextView) findViewById(R.id.stringText));
        TextView intView      = ((TextView) findViewById(R.id.integerText));
        TextView floatView    = ((TextView) findViewById(R.id.floatText));
        TextView longView     = ((TextView) findViewById(R.id.longText));

        stringView.setText(mConfig.text);
        intView.setText(String.valueOf(mConfig.intValue));
        floatView.setText(String.valueOf(mConfig.floatValue));
        longView.setText(String.valueOf(mConfig.longValue));
    }
}
