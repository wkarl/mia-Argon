package de.prosiebensat1digital.argondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.prosiebensat1digital.argon.Argon;

/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 ProSiebenSat.1 Digital GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
