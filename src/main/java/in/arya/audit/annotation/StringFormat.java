package in.arya.audit.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;

/**
 * Created by dev on 07/04/18.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { FIELD, ANNOTATION_TYPE })
public @interface StringFormat {

    String format();
}
