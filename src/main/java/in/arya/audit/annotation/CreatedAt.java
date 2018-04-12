package in.arya.audit.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Timestamp;


@Retention(RetentionPolicy.RUNTIME)
@Target(value = { FIELD, ANNOTATION_TYPE })
public @interface CreatedAt {

	 Class<?> type() default Timestamp.class;
}
