/**
 * 
 */
package com.ctem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

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
	
	@NotBlank
	@Column(unique = true)
	private String code;
	
	@NotBlank
	@Column(unique = true)
	private String name;
	
	@NotBlank
	private Long office_id;

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
	 * @return the office_id
	 */
	public Long getOffice_id() {
		return office_id;
	}

	/**
	 * @param office_id the office_id to set
	 */
	public void setOffice_id(Long office_id) {
		this.office_id = office_id;
	}
	
}
