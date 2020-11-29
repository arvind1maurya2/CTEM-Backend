package com.ctem.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Shashank
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "MasterPermissionAction.getAllActiveMasterPermissionActions", query = "SELECT mpa FROM MasterPermissionAction mpa WHERE mpa.moduleId=:moduleId AND mpa.archived = 'false'")

})
public class MasterPermissionAction extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8766905892051048493L;
	private String actionName;
	private Long moduleId;
	private String moduleName;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean active;
	
	
	
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return the moduleId
	 */
	public Long getModuleId() {
		return moduleId;
	}
	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
