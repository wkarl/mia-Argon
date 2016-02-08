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

import java.util.List;

import de.sevenfactory.argon.annotation.Ignore;
import de.sevenfactory.argon.annotation.Name;
import de.sevenfactory.argon.annotation.OptionNames;
import de.sevenfactory.argon.annotation.Options;

public class Config {
    public boolean showHeadline;
    public String  text;
    public int     intValue;
    public float   floatValue;

    @Name("Long Value")
    public long longValue;

    @Name("List Values")
    @Options({"Option 1", "Option 2", "Option 3"})
    @OptionNames({"Option name 1", "Option name 2", "Option name 3"})
    public String listValue;

    @Ignore
    public String ignoredValue;

    // Any type can be used as a field but only primitives and Strings will be displayed as preferences
    public List<String> list;
}
