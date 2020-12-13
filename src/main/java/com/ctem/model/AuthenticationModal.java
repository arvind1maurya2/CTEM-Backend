/**
 * 
 */
package com.ctem.model;

import com.ctem.entity.UserEntity;

/**
 * @author Arvind Maurya
 *
 */
public class AuthenticationModal {
	
	private String accessToken;
	private String tokenType = "Basic";
	private UserEntity user;
	private Boolean success;
	private String message;
	
	/**
	 * @param accessToken
	 * @param tokenType
	 * @param user
	 * @param success
	 * @param message
	 */
	public AuthenticationModal(String accessToken, UserEntity user, Boolean success, String message) {
		super();
		this.accessToken = accessToken;
		this.user = user;
		this.success = success;
		this.message = message;
	}
	/**
	 * @param success
	 * @param message
	 */
	public AuthenticationModal(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}
	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	/**
	 * @return the user
	 */
	public UserEntity getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}
	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	

}
