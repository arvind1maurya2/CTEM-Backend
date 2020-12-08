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
public class AuthorizationRole {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	 @Column(updatable = true, nullable = false)
    private String name;
    @Column(updatable = true, nullable = true)
    private String description;
    @Column(updatable = true, nullable = true)
    private String permissions;
    @Column(updatable = true, nullable = true)
    private int profileId;
    @Column(updatable = true, nullable = true)
    private Calendar creationDate;
    @Column(updatable = true, nullable = true)
    private int createdBy;
    @Column(updatable = true, nullable = true)
    private Calendar lastUpdateDate;
    @Column(updatable = true, nullable = true)
    private int last_updated_by;
    @Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
    private boolean isActive;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
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
	 * @return the creationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the lastUpdateDate
	 */
	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	/**
	 * @return the last_updated_by
	 */
	public int getLast_updated_by() {
		return last_updated_by;
	}
	/**
	 * @param last_updated_by the last_updated_by to set
	 */
	public void setLast_updated_by(int last_updated_by) {
		this.last_updated_by = last_updated_by;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
    
    

}
