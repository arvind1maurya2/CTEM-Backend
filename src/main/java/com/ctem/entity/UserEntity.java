package com.ctem.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@NamedQueries({ 
	@NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u  ORDER BY u.creationDate DESC"),
	@NamedQuery(name = "UserEntity.getAllUsers", query = "SELECT u FROM UserEntity u WHERE u.role.userTypeId not in('4')  ORDER BY u.creationDate DESC"),
	@NamedQuery(name = "UserEntity.findByUsernameOrEmail", query = "SELECT u FROM UserEntity u where (lower(u.email) = :email OR lower(u.username) = :email) AND u.archived='false'"),
    @NamedQuery(name = "UserEntity.updateUserDeleteStatusByRole", query = "UPDATE UserEntity u set u.archived = :status where u.role.id=:roleId"),
    @NamedQuery(name = "UserEntity.getAllUsersByRole", query = "SELECT u FROM UserEntity u WHERE u.role.userTypeId = :userTypeId ORDER BY u.creationDate DESC"),
})
@Table(name = "user_")
public class UserEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Column(unique = true)
	private String username;
	@JsonIgnore
	@NotBlank
	private String password;
	@JsonIgnore
	@NotBlank
	private String bonitaAccessToken;
	@JsonIgnore
	private boolean enabled;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String mobileNumber;
	private String address;
	// Transient
	@JsonIgnore
	@Transient
	private Long roleId;
	@JsonIgnore
	private String verificationCode;
	@JsonIgnore
	@Column(nullable = false, columnDefinition = "boolean default true")
    private boolean verified;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Role role;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Office.class)
	@JoinTable(name = "USER_OFFICE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "OFFICE_ID") })
	private Office office;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Designation.class)
	@JoinTable(name = "USER_DESIGNATION", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "DESIGNATION_ID") })
	private Designation designation;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Division.class)
	@JoinTable(name = "USER_DIVISION", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "DIVISION_ID") })
	private Division division;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = District.class)
	@JoinTable(name = "USER_DISTRICT", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "DISTRICT_ID") })
	private District district;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = State.class)
	@JoinTable(name = "USER_STATE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "STATE_ID") })
	private State state;

	public UserEntity() {
		super();
	}

	public UserEntity(String email, String avtarImage, String firstName, String middleName, String lastName, String username,
			String gender) {
		super();
		this.email = email;
		//this.avtarImage = avtarImage;
		this.firstName = firstName;
		//this.middleName = middleName;
		this.lastName = lastName;
		this.username = username;
		this.gender = gender;
	}

	public UserEntity(String email, String firstName, String middleName, String lastName, String username, String password,
			Role role) {
		super();
		this.email = email;
		this.firstName = firstName;
		//this.middleName = middleName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public UserEntity(String email, String firstName, String lastName, String username, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the bonitaAccessToken
	 */
	public String getBonitaAccessToken() {
		return bonitaAccessToken;
	}

	/**
	 * @param bonitaAccessToken the bonitaAccessToken to set
	 */
	public void setBonitaAccessToken(String bonitaAccessToken) {
		this.bonitaAccessToken = bonitaAccessToken;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
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
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the verificationCode
	 */
	public String getVerificationCode() {
		return verificationCode;
	}

	/**
	 * @param verificationCode the verificationCode to set
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = true;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the office
	 */
	public Office getOffice() {
		return office;
	}

	/**
	 * @param office the office to set
	 */
	public void setOffice(Office office) {
		this.office = office;
	}

	/**
	 * @return the designation
	 */
	public Designation getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	/**
	 * @return the division
	 */
	public Division getDivision() {
		return division;
	}

	/**
	 * @param division the division to set
	 */
	public void setDivision(Division division) {
		this.division = division;
	}

	/**
	 * @return the district
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(District district) {
		this.district = district;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}


}