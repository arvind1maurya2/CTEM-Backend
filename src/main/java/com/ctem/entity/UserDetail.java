package com.ctem.entity;

public class UserDetail {
	private Long id;
	  private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String office;
	private String designation;
	private String email;
	private String mobileNumber;
	private String address;
	private String role;
	private String division;
	private String gender;
	private String district;
	private String state;
	private boolean enable;
	private Office officeObj;
	private Role roleObj;
	private Division divisionObj;
	private String roleName;
	private String divisionName;
	private String officeName;
	
	
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	/**
	 * @return the officeObj
	 */
	public Office getOfficeObj() {
		return officeObj;
	}
	/**
	 * @param officeObj the officeObj to set
	 */
	public void setOfficeObj(Office officeObj) {
		this.officeObj = officeObj;
	}
	/**
	 * @return the roleObj
	 */
	public Role getRoleObj() {
		return roleObj;
	}
	/**
	 * @param roleObj the roleObj to set
	 */
	public void setRoleObj(Role roleObj) {
		this.roleObj = roleObj;
	}
	/**
	 * @return the divisionObj
	 */
	public Division getDivisionObj() {
		return divisionObj;
	}
	/**
	 * @param divisionObj the divisionObj to set
	 */
	public void setDivisionObj(Division divisionObj) {
		this.divisionObj = divisionObj;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}
	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
}
