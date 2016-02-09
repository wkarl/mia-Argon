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
    @Name("Show text")
    public boolean showHeadline;

    @Name("Text")
    public String text;

    @Name("Text color")
    @Options({"#000000", "#00ff00", "#ff0000"})
    @OptionNames({"black", "green", "red"})
    public String textColor;

    @Name("Text size")
    @Options({"10", "18", "24", "36", "72"})
    @OptionNames({"10sp", "18sp", "24sp", "36sp", "72sp"})
    public float textSize;

    @Name("Text gravity")
    @OptionNames({"default", "CENTER"})
    @Options({ "51", "17"})
    public int textGravity;

    @Name("Text layout_height")
    @Options({"-1", "-2"})
    @OptionNames({"MATCH_PARENT", "WRAP_CONTENT"})
    public int textLayoutHeight;

    @Name("Text layout_width")
    @Options({"-1", "-2"})
    @OptionNames({"MATCH_PARENT", "WRAP_CONTENT"})
    public int textLayoutWidth;

    @Name("Background color")
    @Options({"#ffffff", "#aaaaaa"})
    @OptionNames({"white", "grey"})
    public String backgroundColor;

    @Ignore
    public String ignoredValue;

    // Any type can be used as a field but only primitives and Strings will be displayed as preferences
    public List<String> list;
}
