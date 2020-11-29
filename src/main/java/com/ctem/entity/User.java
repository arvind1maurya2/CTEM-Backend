package com.ctem.entity;
import java.sql.Date;

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
import javax.validation.constraints.Size;


@Entity
@NamedQueries({ 
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u  ORDER BY u.creationDate DESC"),
	@NamedQuery(name = "User.getAllUsers", query = "SELECT u FROM User u WHERE u.role.userTypeId not in('1','2')  ORDER BY u.creationDate DESC"),
	@NamedQuery(name = "User.findByUsernameOrEmail", query = "SELECT u FROM User u where (lower(u.email) = :email OR lower(u.username) = :email) AND u.archived='false'"),
    @NamedQuery(name = "User.updateUserDeleteStatusByRole", query = "UPDATE User u set u.archived = :status where u.role.id=:roleId"),
    @NamedQuery(name = "User.getAllUsersByRole", query = "SELECT u FROM User u WHERE u.role.userTypeId = :userTypeId ORDER BY u.creationDate DESC"),
})
@Table(name = "user_")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Size(min = 3, max = 100)
	@Column(unique = true)
	private String email;
	
	private String avtarImage;
	
	@NotBlank
	@Size(min = 3, max = 40)
	private String firstName;
	
	@Size(min = 1, max = 40)
	private String middleName;
	
	@NotBlank
	@Size(min = 3, max = 40)
	private String lastName;
	
	@Size(min = 3, max = 50)
	private String username;
	
	private String gender;
	
	private String password;
	
	private String verificationCode;
	private Date expirationTime;
    private boolean verified;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean firstLogin;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Role role;
    
    private String userCode;

	// Transient
    
	@Transient
	private Long roleId;
	

	public User() {
		super();
	}

	/**
	 * @param email
	 * @param avtarImage
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param userName
	 * @param gender
	 */
	public User(String email, String avtarImage, String firstName, String middleName, String lastName, String username,
			String gender) {
		super();
		this.email = email;
		this.avtarImage = avtarImage;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.username = username;
		this.gender = gender;
	}

	/**
	 * @param email
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param userName
	 * @param password
	 * @param roles
	 */
	public User(String email, String firstName, String middleName, String lastName, String username, String password,
			Role role) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	/**
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param userName
	 * @param password
	 * @param roles
	 */
	public User(String email, String firstName, String lastName, String username, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
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
	 * @return the avtarImage
	 */
	public String getAvtarImage() {
		return avtarImage;
	}

	/**
	 * @param avtarImage the avtarImage to set
	 */
	public void setAvtarImage(String avtarImage) {
		this.avtarImage = avtarImage;
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
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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
	 * @return the roles
	 */

	public Role getRole() {
		return role;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
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
	 * @return the expirationTime
	 */
	public Date getExpirationTime() {
		return expirationTime;
	}

	/**
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
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
		this.verified = verified;
	}

	/**
	 * @return the firstLogin
	 */
	public boolean isFirstLogin() {
		return firstLogin;
	}

	/**
	 * @param firstLogin the firstLogin to set
	 */
	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	

}