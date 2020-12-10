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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Arvind Maurya
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Office.getAllOffices", query = "SELECT o.id,o.code,o.name,o.type,o.mobileNumber,o.address,d.id as district, d.name as districtName,c.id as city,c.name as cityName FROM Office o, District d, City c WHERE o.district=d.id and o.city=c.id"), })
public class Office extends BaseEntity {

	private static final long serialVersionUID = -2263208241269807057L;

	@Column(updatable = true, nullable = true)
	private String code;
	@Column(updatable = true, nullable = false, unique = true)
	private String name;
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private String type;
	@JsonIgnore
	@Column(updatable = true, nullable = true)
	private String mobileNumber;
	@Column(updatable = true, nullable = true)
	private String address;
	@Column(updatable = true, nullable = true)
	private Long district;
	@Column(updatable = true, nullable = true)
	private Long city;
	@JsonIgnore
	@Column(updatable = true, nullable = false, columnDefinition = "integer default 1")
	private int sortOrder;
	@JsonIgnore
	@Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
	private boolean enable;

	@Transient
	private String districtName;
	@Transient
	private String cityName;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the district
	 */
	public Long getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(Long district) {
		this.district = district;
	}

	/**
	 * @return the city
	 */
	public Long getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(Long city) {
		this.city = city;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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

	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
}
