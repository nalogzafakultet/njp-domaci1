package org.sekularac.njp.annotations.field;

import org.sekularac.njp.annotations.enums.EnumeratedType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Enumerated {
    EnumeratedType value() default EnumeratedType.ORDINAL;
}
