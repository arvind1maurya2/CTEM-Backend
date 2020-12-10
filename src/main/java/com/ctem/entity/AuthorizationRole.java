/**
 * 
 */
package com.ctem.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author ARVIND MAURYA
 *
 */
@Entity
@Table(name = "authorization_role")
public class AuthorizationRole extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 @Column(updatable = true, nullable = false)
    private String name;
    @Column(updatable = true, nullable = true)
    private String description;
    @Column(updatable = true, nullable = true)
    private String permissions;
    @Column(updatable = true, nullable = true)
    private int profileId;
    @Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
    private boolean enable;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the permissions
	 */
	public String getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	/**
	 * @return the profileId
	 */
	public int getProfileId() {
		return profileId;
	}
	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}
	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

    
}
