/**
 * 
 */
package com.ctem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 * @author Arvind Maurya
 *
 */
@Entity
public class District extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Column(unique = true)
	private String code;
	@NotBlank
	@Column(unique = true)
	private String name;
	
	@NotBlank
	private Long stateId;
	
	@Transient
	private List<City> cities;
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
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the cities
	 */
	public List<City> getCities() {
		return cities;
	}
	/**
	 * @param cities the cities to set
	 */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

}
