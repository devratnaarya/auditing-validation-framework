package in.arya.audit.model;

/**
 ** Created by dev on 07/04/18.
 */

public interface AuditModel {

	public void setCreatedBy(String createdBy);

	public void setCreatedByName(String createdByName);

	public void setUpdatedBy(String updatedBy);

	public void setUpdatedByName(String updateByName);
	
	public void checkValidity();

}
