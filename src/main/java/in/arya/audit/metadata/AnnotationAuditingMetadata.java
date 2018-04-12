package in.arya.audit.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import in.arya.audit.annotation.*;
import in.arya.audit.model.AuditModel;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter;
import org.springframework.util.Assert;

import lombok.Data;

@Data
public class AnnotationAuditingMetadata implements AuditModel {

	private static final AnnotationFieldFilter CREATED_BY = new AnnotationFieldFilter(CreatedBy.class);
	private static final AnnotationFieldFilter UPDATED_BY = new AnnotationFieldFilter(UpdatedBy.class);
	private static final AnnotationFieldFilter CREATED_BY_NAME = new AnnotationFieldFilter(CreatedByName.class);
	private static final AnnotationFieldFilter UPDATED_BY_NAME = new AnnotationFieldFilter(UpdatedByName.class);
	private static final AnnotationFieldFilter CREATED_BY_USER = new AnnotationFieldFilter(CreatedByUser.class);
	private static final AnnotationFieldFilter UPDATED_BY_USER = new AnnotationFieldFilter(UpdatedByUser.class);

	private static final Map<Class<?>, AnnotationAuditingMetadata> METADATA_CACHE = new ConcurrentHashMap<>();

	private final Field createdBy;
	private final Field updatedBy;
	private final Field updateByName;
	private final Field createdByName;
	private final Field updateByUser;
	private final Field createdByUser;
	private final List<Field> mobileNumberFileds;
	private final List<Field> phoneNumberFileds;
    private final List<Field> decimalFileds;
    private final List<Field> EmailFileds;
    private final List<Field> notBlankFileds;
    private final List<Field> notEmptyFileds;
    private final List<Field> StringFileds;

	private Object target;

    private Map<Class<? extends Annotation>, List<Field>> VALIDATION_FIELD_MAP;

	private AnnotationAuditingMetadata(Class<?> type) {

		Assert.notNull(type, "Given type must not be null!");

		this.createdBy = ReflectionUtils.findField(type, CREATED_BY);
		this.updatedBy = ReflectionUtils.findField(type, UPDATED_BY);
		this.updateByName = ReflectionUtils.findField(type, UPDATED_BY_NAME);
		this.createdByName = ReflectionUtils.findField(type, CREATED_BY_NAME);
		this.updateByUser = ReflectionUtils.findField(type, UPDATED_BY_USER);
		this.createdByUser = ReflectionUtils.findField(type, CREATED_BY_USER);
		this.mobileNumberFileds = getFieldsListWithAnnotation(type, MobileNumber.class);
        this.phoneNumberFileds = getFieldsListWithAnnotation(type, PhoneNumber.class);
        this.decimalFileds = getFieldsListWithAnnotation(type, DecimalFormat.class);
        this.EmailFileds = getFieldsListWithAnnotation(type, Email.class);
        this.notBlankFileds = getFieldsListWithAnnotation(type, NotBlank.class);
        this.notEmptyFileds = getFieldsListWithAnnotation(type, NotEmpty.class);
        this.StringFileds = getFieldsListWithAnnotation(type, StringFormat.class);


        VALIDATION_FIELD_MAP = new HashMap<Class<? extends Annotation>, List<Field>>() {
            {
                put(MobileNumber.class, mobileNumberFileds);
                put(PhoneNumber.class, phoneNumberFileds);
                put(DecimalFormat.class, decimalFileds);
                put(Email.class, EmailFileds);
                put(NotBlank.class, notBlankFileds);
                put(NotEmpty.class, notEmptyFileds);
                put(StringFormat.class, StringFileds);

            }
        };
	}

	public static AuditModel getMetadata(Object target) {
		Class<?> type = target.getClass();
		if (METADATA_CACHE.containsKey(type)) {
			AnnotationAuditingMetadata metadata = METADATA_CACHE.get(type);
			metadata.setTarget(target);
			return metadata;
		}
		AnnotationAuditingMetadata metadata = new AnnotationAuditingMetadata(type);
		
		metadata.setTarget(target);
		METADATA_CACHE.put(type, metadata);
		return metadata;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		setField(this.createdBy, createdBy);
	}

	@Override
	public void setCreatedByName(String createdByName) {
		setField(this.createdByName, createdByName);
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		setField(this.updatedBy, updatedBy);
	}

	@Override
	public void setUpdatedByName(String updateByName) {
		setField(this.updateByName, updateByName);
	}

	@Override
	public void checkValidity() {
		checkValidity(this.getTarget());
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	private void setField(Field field, Object value) {
		if (field != null) {
			ReflectionUtils.setField(field, this.target, value);
		}
	}
	
	private void checkValidity(Object obj) {
		try {
//			if (this.mobileNumberFileds != null && !this.mobileNumberFileds.isEmpty()) {
//				for (Field mobileNumber : mobileNumberFileds) {
//                    Annotation[] annotations = mobileNumber.getAnnotations();
//                    for (Annotation annotation : annotations) {
//                        List<Field> a = VALIDATION_FIELD_MAP.get(annotation);
//
//                    }
//					MobileNumber mobile = mobileNumber.getAnnotation(MobileNumber.class);
//					if (mobile != null && mobile.validation()) {
//                        mobileNumber.setAccessible(true);
//						Object value = mobileNumber.get(obj);
//						if (value != null && !value.toString().matches("[0-9\\- ]+")) {
//							//throw new IllegalArgumentException("Invelid field "+mobileField.getName()+" for : "+ value.toString());
//						}
//					}
//				}
//			}
            VALIDATION_FIELD_MAP.entrySet().parallelStream()
            .forEach(entry -> {
                validate.isValid(getTarget(),entry.getValue(), entry.getKey());
            });

		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<Field> getFieldsListWithAnnotation(final Class<?> cls,
			final Class<? extends Annotation> annotationCls) {
		Validate.isTrue(annotationCls != null, "The annotation class can not be null!");
		final Field[] allFields = cls.getDeclaredFields();
		final List<Field> annotatedFields = new ArrayList<>();
		for (final Field field : allFields) {
			if (field.getAnnotation(annotationCls) != null) {
				annotatedFields.add(field);
			}
		}
		return annotatedFields;
	}

	@Autowired
    private in.arya.audit.factory.Validate.Validate validate;
}
