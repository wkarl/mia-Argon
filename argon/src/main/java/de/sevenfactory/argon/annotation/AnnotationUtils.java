package de.sevenfactory.argon.annotation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;

public class AnnotationUtils {
    public static String getAnnotatedName(@NonNull Field field) {
        Name name = field.getAnnotation(Name.class);

        if (name != null) {
            return name.value();
        } else {
            return field.getName();
        }
    }

    @Nullable
    public static String[] getAnnotatedOptionNames(@NonNull Field field) {
        OptionNames optionNames = field.getAnnotation(OptionNames.class);

        if (optionNames != null) {
            return optionNames.value();
        } else {
            return null;
        }
    }

    @Nullable
    public static String[] getAnnotatedOptions(@NonNull Field field) {
        Options options = field.getAnnotation(Options.class);

        if (options != null) {
            return options.value();
        } else {
            return null;
        }
    }
}
