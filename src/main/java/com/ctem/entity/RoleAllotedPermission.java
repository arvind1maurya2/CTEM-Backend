package com.ctem.entity;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author Arvind Maurya
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "RoleAllotedPermission.deleteAllAllotedPermissionByRole", query = "DELETE FROM RoleAllotedPermission rap WHERE rap.roleId = :roleId"),
	@NamedQuery(name = "RoleAllotedPermission.findAllAllotedPermissionByRole", query = "SELECT rap FROM RoleAllotedPermission rap WHERE rap.roleId = :roleId")

})
public class RoleAllotedPermission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6070276111609904201L;
	private Long moduleId;
	private Long actionId;
	private String actionName;
	private String moduleName;
	private Long roleId;
	private boolean active;
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
	 * @return the actionId
	 */
	public Long getActionId() {
		return actionId;
	}
	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
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
	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
