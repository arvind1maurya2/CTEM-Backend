package com.ctem.entity;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Shashank
 *
 */

@Entity 
@NamedQueries({ 
	@NamedQuery(name = "Role.getActiveRoles", query = "SELECT r fROM Role r WHERE r.archived='false' AND r.id not in ('1') ORDER BY r.creationDate DESC"),
	@NamedQuery(name = "Role.getAllRoles", query = "SELECT r fROM Role r WHERE r.id not in ('1') ORDER BY r.creationDate DESC"),
	@NamedQuery(name = "Role.existRoleByRoleName", query = "SELECT r fROM Role r WHERE r.name = :roleName"),
})


public class Role extends BaseEntity {

	/**
	* 
	*/
	private static final long serialVersionUID = -2263208241269807057L;

	@Column(length = 60, unique = true)
	private String name;
	@JsonIgnore
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean systemRole;
	@JsonIgnore
	private Long userTypeId;
	private String Description;
	
	/*
	 * userTypeId 1 = Super Admin
	 * userTypeId 2 = Project Director
	 * userTypeId 3 = Project Manager
	 * userTypeId 4 = Head Of Department
	 * userTypeId 5 = Requisition Employee
	 * userTypeId 6 = PQ Team
	 * userTypeId 7 = Procurement Officer
	 * userTypeId 8 = Store Keeper
	 * userTypeId 9 = Supplier
	  */
	@JsonIgnore
	@Transient
	private List<RoleAllotedPermission> roleAllotedPermission;


	/**
	 * 
	 */
	public Role() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the roleAllotedPermission
	 */
	public List<RoleAllotedPermission> getRoleAllotedPermission() {
		return roleAllotedPermission;
	}

	/**
	 * @param roleAllotedPermission the roleAllotedPermission to set
	 */
	public void setRoleAllotedPermission(List<RoleAllotedPermission> roleAllotedPermission) {
		this.roleAllotedPermission = roleAllotedPermission;
	}

	/**
	 * @return the systemRole
	 */
	public boolean isSystemRole() {
		return systemRole;
	}

	/**
	 * @param systemRole the systemRole to set
	 */
	public void setSystemRole(boolean systemRole) {
		this.systemRole = systemRole;
	}

	/**
	 * @return the userTypeId
	 */
	public Long getUserTypeId() {
		return userTypeId;
	}

	/**
	 * @param userTypeId the userTypeId to set
	 */
	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}

}
