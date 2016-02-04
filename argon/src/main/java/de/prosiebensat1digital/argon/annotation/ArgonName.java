package de.prosiebensat1digital.argon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bernd on 04.02.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ArgonName {
    /**
     * @return the desired name of the field in the config activity
     */
    String name();
}
