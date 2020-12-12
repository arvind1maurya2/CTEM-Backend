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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Arvind Maurya
 *
 */
@Entity
public class Designation extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Column(unique = true)
	private String name;
	
	private Long office_id;
	
	private Long division_id;

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

	/**
	 * @return the division_id
	 */
	public Long getDivision_id() {
		return division_id;
	}

	/**
	 * @param division_id the division_id to set
	 */
	public void setDivision_id(Long division_id) {
		this.division_id = division_id;
	}

}
