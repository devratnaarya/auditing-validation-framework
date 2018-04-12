package in.arya.audit.listener;



import in.arya.audit.auditor.service.AuditorAware;
import in.arya.audit.factory.AuditableBeanWrapperFactory;
import in.arya.audit.model.AuditModel;
import in.arya.audit.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Service
public class AuditEntityListener {

	@Autowired
	public AuditEntityListener(AuditorAware<User> auditorAware) {
		this.auditorAware = auditorAware;
	}

	@PrePersist
	public void onPrePersist(Object obj) {
		populateCreate(obj);
	}

	@PreUpdate
	public void onPreUpdate(Object obj) {
		populateUpdate(obj);
	}

	protected void populateCreate(Object obj) {
		if (obj != null) {
			AuditModel auditModel = AuditableBeanWrapperFactory.getBeanWrapperFor(obj);

			User user = auditorAware.getCurrentAuditor();
			if (user != null) {
				auditModel.setUpdatedBy(String.valueOf(user.getId()));
				auditModel.setCreatedBy(String.valueOf(user.getId()));
				auditModel.setCreatedByName(user.getFullName());
				auditModel.setUpdatedByName(user.getFullName());
				auditModel.checkValidity();
			}
		}
	}


	protected void populateUpdate(Object obj) {
		if (obj != null) {
			AuditModel auditModel = AuditableBeanWrapperFactory.getBeanWrapperFor(obj);
			User user = auditorAware.getCurrentAuditor();
			if (user != null) {
				auditModel.setUpdatedBy(String.valueOf(user.getId()));
				auditModel.setUpdatedByName(user.getFullName());
			}
			auditModel.checkValidity();
		}
	}

	private final AuditorAware<User> auditorAware;
}
