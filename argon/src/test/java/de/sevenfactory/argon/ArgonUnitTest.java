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
