/**
 * 
 */
package com.ctem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 * @author Arvind Maurya
 *
 */
@Entity
public class Office extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2263208241269807057L;

	@NotBlank
	@Column(unique = true)
	private String code;

	@NotBlank
	@Column(unique = true)
	private String name;
	
	@NotBlank
	private String type;
	
	@NotBlank
	private String mobileNumber;
	
	@NotBlank
	private String address;
	
	private Long district;
	
	private Long city;
	
	@Column(columnDefinition = "bigint default 1")
	private Long state;
 
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
	 * @return the state
	 */
	public Long getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		this.state = state;
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
