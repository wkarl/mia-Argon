/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 ProSiebenSat.1 Digital GmbH
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.sevenfactory.argondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.sevenfactory.argon.Argon;

public class MainActivity extends AppCompatActivity {
    private Config mConfig;
    
    private TextView mHeadlineView;
    private Button mDebugToggleButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get config from Argon
        mConfig = Argon.getConfig();
        
        // Find views
        mHeadlineView = (TextView) findViewById(R.id.headline);
        mDebugToggleButton = (Button) findViewById(R.id.button_debug_toggle);

        mHeadlineView.setText(mConfig.showHeadline ? "Hello World!" : null);
    }

    public void goToDetails(View v) {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }

    public void toggleDebugMode(View v) {
        Argon.setDebugModeEnabled(!Argon.isDebugModeEnabled());
        mDebugToggleButton.setText(Argon.isDebugModeEnabled() ? R.string.debug_disable : R.string.debug_enable);
    }
}
