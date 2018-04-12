package in.arya.audit.factory.Validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by dev on 08/04/18.
 */
public interface Validate {

    boolean isValid(Object o, List<Field> fields, Class<? extends Annotation> annotationCls) throws IllegalAccessException;

}
