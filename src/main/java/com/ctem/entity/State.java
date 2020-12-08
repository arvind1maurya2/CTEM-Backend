/**
 * 
 */
package com.ctem.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Arvind Maurya
 *
 */
@Entity
public class State {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "persistenceid")
	private Long id;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private Long persistenceversion;
	
	@JsonIgnore
	@Column(updatable = true, nullable = false)
	private String code;
	
	@Column(updatable = true, nullable = false)
	private String name;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private int sortOrder;

	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private Calendar creationDate;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private int createdBy;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private Calendar lastUpdateDate;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private int last_updated_by;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true, columnDefinition = "boolean default false")
	private boolean delete;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
	private boolean active;


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

	public Long getPersistenceversion() {
		return persistenceversion;
	}

	public void setPersistenceversion(Long persistenceversion) {
		this.persistenceversion = persistenceversion;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(int last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	

}
