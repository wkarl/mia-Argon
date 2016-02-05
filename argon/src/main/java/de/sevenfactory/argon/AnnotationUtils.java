package de.sevenfactory.argon;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;

import de.sevenfactory.argon.annotation.Ignore;
import de.sevenfactory.argon.annotation.Name;
import de.sevenfactory.argon.annotation.OptionNames;
import de.sevenfactory.argon.annotation.Options;

class AnnotationUtils {
    static boolean shouldIgnore(@NonNull Field field) {
        Ignore shouldIgnore = field.getAnnotation(Ignore.class);

        if (shouldIgnore != null) {
            return shouldIgnore.value();
        } else {
            return false;
        }
    }

    static String getAnnotatedName(@NonNull Field field) {
        Name name = field.getAnnotation(Name.class);

        if (name != null) {
            return name.value();
        } else {
            return field.getName();
        }
    }

    @Nullable
    static String[] getAnnotatedOptionNames(@NonNull Field field) {
        OptionNames optionNames = field.getAnnotation(OptionNames.class);

        if (optionNames != null) {
            return optionNames.value();
        } else {
            return null;
        }
    }

    @Nullable
    static String[] getAnnotatedOptions(@NonNull Field field) {
        Options options = field.getAnnotation(Options.class);

        if (options != null) {
            return options.value();
        } else {
            return null;
        }
    }
}
