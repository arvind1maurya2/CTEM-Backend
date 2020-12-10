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
public class Division extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@JsonIgnore
	@Column(updatable = true, nullable = false)
	private String code;
	@Column(updatable = true, nullable = false)
	private String name;
	@JsonIgnore
	@Column(updatable = true, nullable = false)
	private String office_id;
	@JsonIgnore
	@Column(updatable = true, nullable = false)
	private String district_id;
	@JsonIgnore
	@Column(updatable = true, nullable = false)
	private int city_id;
	@JsonIgnore
	private int sortOrder;
	@JsonIgnore
	@Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
	private boolean enable;
	
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

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
