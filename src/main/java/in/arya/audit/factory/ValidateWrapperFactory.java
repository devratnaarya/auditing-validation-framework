package in.arya.audit.factory;

import in.arya.audit.annotation.*;
import in.arya.audit.factory.Validate.Validate;
import in.arya.audit.util.CommonUtil;
import org.springframework.data.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dev on 08/04/18.
 */
public class ValidateWrapperFactory implements Validate {

    private static final String CLASS_INT = Integer.class.getSimpleName();
    private static final String CLASS_LONG = Long.class.getSimpleName();
    private static final String CLASS_FLOAT = Float.class.getSimpleName();
    private static final String CLASS_DOUBLE = Double.class.getSimpleName();


    @Override
    public boolean isValid(Object o, List<Field> fields, Class<? extends Annotation> annotationCls) throws IllegalAccessException {
        org.apache.commons.lang3.Validate.isTrue(annotationCls != null, "The annotation class can not be null!");
        org.apache.commons.lang3.Validate.isTrue(fields != null && !fields.isEmpty(), "The target fields can not be null!");
        if (annotationCls.equals(MobileNumber.class)) {
            for (Field field : fields) {
                try {
                    MobileNumber mobile = field.getAnnotation(MobileNumber.class);
                    if (mobile.validation()) {
                        field.setAccessible(true);
                        Object value = field.get(o);
                        if (value != null && !value.toString().matches("[0-9]+")) {
                            throw new IllegalArgumentException("Invalid field " + field.getName() + " for : " + value.toString());
                        }
                    }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        } else if (annotationCls.equals(PhoneNumber.class)) {
            for (Field field : fields) {
                try {
                    PhoneNumber phoneNumber = field.getAnnotation(PhoneNumber.class);
                    if (phoneNumber.validation()) {
                        field.setAccessible(true);
                        Object value = field.get(o);
                        if (value != null && !value.toString().matches("[0-9\\- ]+")) {
                            throw new IllegalArgumentException("Invalid field " + field.getName() + " for : " + value.toString());
                        }
                    }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        } else if (annotationCls.equals(Email.class)) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(o);
                    if (value != null && value.toString().contains("@")) {
                        throw new IllegalArgumentException("Invalid field " + field.getName() + " for : " + value.toString());
                    }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        } else if (annotationCls.equals(DecimalFormat.class)) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    DecimalFormat decimalFormat = field.getAnnotation(DecimalFormat.class);
                    ReflectionUtils.setField(field, o, CommonUtil.parseValue(o, field, decimalFormat.scale()));;
                } catch (IllegalAccessException e) {
                    throw e;
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        } else if (annotationCls.equals(StringFormat.class)) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    StringFormat stringFormat = field.getAnnotation(StringFormat.class);

                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        }

        return false;
    }
}
