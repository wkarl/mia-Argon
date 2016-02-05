package de.sevenfactory.argon;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.text.InputType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class PreferenceFactory {

    static Preference createPreference(Context context, Object config, Field field) throws IllegalAccessException {
        Preference pref = null;

        if (Modifier.isPublic(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
            String   name        = AnnotationUtils.getAnnotatedName(field);
            String[] options     = AnnotationUtils.getAnnotatedOptions(field);
            String[] optionNames = AnnotationUtils.getAnnotatedOptionNames(field);

            if (options != null) {
                // List
                String value = String.valueOf(field.get(config));
                pref = createListPreference(context, name, options, optionNames, value);
            } else {
                // Checkbox
                if (field.getType().equals(boolean.class)) {
                    boolean value = (boolean) field.get(config);
                    pref = createCheckBoxPreference(context, name, value);
                }

                // Text
                if (field.getType().equals(String.class)) {
                    String value = (String) field.get(config);
                    pref = createEditTextPreference(context, name, value, InputType.TYPE_CLASS_TEXT);
                }

                // Number
                if (field.getType().equals(int.class) || field.getType().equals(long.class) || field.getType().equals(float.class)) {
                    String value = String.valueOf(field.get(config));
                    pref = createEditTextPreference(context, name, value, InputType.TYPE_CLASS_NUMBER);
                }
            }
        }

        return pref;
    }

    private static ListPreference createListPreference(Context context, String name, String[] options, String[] optionNames, String value) {
        ListPreference pref = new ListPreference(context);
        pref.setTitle(name);
        pref.setEntries(optionNames != null ? optionNames : options);
        pref.setEntryValues(options);
        pref.setValue(value);

        return pref;
    }

    private static CheckBoxPreference createCheckBoxPreference(Context context, String title, boolean checked) {
        CheckBoxPreference pref = new CheckBoxPreference(context);
        pref.setTitle(title);
        pref.setChecked(checked);

        return pref;
    }

    private static EditTextPreference createEditTextPreference(Context context, String title, String text, int inputType) {
        EditTextPreference pref = new EditTextPreference(context);
        pref.setTitle(title);
        pref.setText(text);
        pref.getEditText().setInputType(inputType);

        return pref;
    }
}
