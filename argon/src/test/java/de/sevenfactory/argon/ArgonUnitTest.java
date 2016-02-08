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

package de.sevenfactory.argon;

import junit.framework.Assert;

import org.junit.Test;

import de.sevenfactory.argon.annotation.Ignore;
import de.sevenfactory.argon.annotation.Name;
import de.sevenfactory.argon.annotation.OptionNames;
import de.sevenfactory.argon.annotation.Options;

public class ArgonUnitTest extends Assert {
    @Test
    public void testAnnotationUtils() throws NoSuchFieldException {
        assertEquals("aString", AnnotationUtils.getAnnotatedName(TestModel.class.getField("aString")));
        assertEquals("int", AnnotationUtils.getAnnotatedName(TestModel.class.getField("anInt")));
        assertEquals(false, AnnotationUtils.shouldIgnore(TestModel.class.getField("anInt")));
        assertEquals(true, AnnotationUtils.shouldIgnore(TestModel.class.getField("anIgnoredString")));
        assertEquals("zero", AnnotationUtils.getAnnotatedOptionNames(TestModel.class.getField("aFloat"))[1]);
        assertEquals("5.0", AnnotationUtils.getAnnotatedOptions(TestModel.class.getField("aFloat"))[2]);
    }

    private static final class TestModel {
        public String aString;

        @Ignore
        public String anIgnoredString;

        @Name("int")
        public int anInt;

        @OptionNames({"negative", "zero", "positive"})
        @Options({"-5.0", "0.0", "5.0"})
        public float aFloat;
    }
}
