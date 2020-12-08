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
//@Table(name = "office")
public class Office extends BaseEntity {
	
	private static final long serialVersionUID = -2263208241269807057L;
	
	
	
	
	//@JsonIgnore
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//private Long persistenceid;
	
	//@JsonIgnore
	//@Column(updatable = true, nullable = true)
	//private Long persistenceversion;
	
	@Column(updatable = true, nullable = true)
	private String code;
	
	@Column(updatable = true, nullable = false)
	private String name;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private String type;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private String phone_number;
	
	@Column(updatable = true, nullable = true)
	private String address;
	
	@Column(updatable = true, nullable = true)
	private String district_id;
	
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private String city_id;

	@Column(updatable = true, nullable = true)
	private String state_id;

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
	

	/*
	 * public Long getPersistenceid() { return persistenceid; }
	 * 
	 * public void setPersistenceid(Long persistenceid) { this.persistenceid =
	 * persistenceid; }
	 */
	

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
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
