package in.arya.audit.factory;

import in.arya.audit.metadata.AnnotationAuditingMetadata;
import in.arya.audit.model.AuditModel;

/**
 * Created by dev on 07/04/18.
 */
public class AuditableBeanWrapperFactory {

    public static AuditModel getBeanWrapperFor(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof AuditModel) {
            return (AuditModel) source;
        }
        return AnnotationAuditingMetadata.getMetadata(source);
    }
}
