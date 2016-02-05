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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import de.sevenfactory.argon.Argon;

public class DetailsActivity extends AppCompatActivity {
    private Config mConfig;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        
        // Get config from Argon
        mConfig = Argon.getConfig();
        
        // Find views
        
        TextView stringView = ((TextView) findViewById(R.id.stringText));
        TextView intView    = ((TextView) findViewById(R.id.integerText));
        TextView floatView  = ((TextView) findViewById(R.id.floatText));
        TextView longView   = ((TextView) findViewById(R.id.longText));
        TextView listView   = ((TextView) findViewById(R.id.listText));

        stringView.setText(mConfig.text);
        intView.setText(String.valueOf(mConfig.intValue));
        floatView.setText(String.valueOf(mConfig.floatValue));
        longView.setText(String.valueOf(mConfig.longValue));
        listView.setText(String.valueOf(mConfig.listValue));
    }
}
