/**
 * 
 */
package com.ctem.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Arvind Maurya
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "City.findDistrictId", query = "SELECT c FROM City c WHERE c.districtId = :districtId order by c.name")

})
public class City extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private String code;
	private  String name;
	@JsonIgnore
	private Long districtId;
	/**
	 * @return the districtId
	 */
	public Long getDistrictId() {
		return districtId;
	}
	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
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
	
}