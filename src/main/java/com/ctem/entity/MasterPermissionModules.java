package com.ctem.entity;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * 
 * @author Arvind Maurya
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "MasterPermissionModules.getAllActiveMasterPermissions", query = "SELECT mpm FROM MasterPermissionModules mpm WHERE mpm.archived = 'false'")

})
public class MasterPermissionModules extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2016580694280895187L;
	
	private String moduleName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = MasterPermissionAction.class)
	@JoinTable(name = "MASTER_PERMISSIONS_MODULE_ACTION",joinColumns = { @JoinColumn(name = "MODULE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "MASTER_PERMISSION_ACTION_ID") })
	private List<MasterPermissionAction> masterPermissionAction;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean active;
	
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
	 * @return the masterPermissionAction
	 */
	public List<MasterPermissionAction> getMasterPermissionAction() {
		return masterPermissionAction;
	}

	/**
	 * @param masterPermissionAction the masterPermissionAction to set
	 */
	public void setMasterPermissionAction(List<MasterPermissionAction> masterPermissionAction) {
		this.masterPermissionAction = masterPermissionAction;
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
