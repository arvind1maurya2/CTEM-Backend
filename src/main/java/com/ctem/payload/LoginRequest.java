package com.ctem.payload;
import javax.validation.constraints.NotBlank;

/**
 * @author Shashank
 *
 */
public class LoginRequest {

	@NotBlank
	private String userNameOrEmail;
	@NotBlank
	private String password;
	/**
	 * @return the userNameOrEmail
	 */
	public String getUserNameOrEmail() {
		return userNameOrEmail;
	}
	/**
	 * @param userNameOrEmail the userNameOrEmail to set
	 */
	public void setUserNameOrEmail(String userNameOrEmail) {
		this.userNameOrEmail = userNameOrEmail;
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
	
	

}
