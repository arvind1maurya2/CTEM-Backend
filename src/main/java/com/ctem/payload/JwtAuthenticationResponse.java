package com.ctem.payload;
import org.apache.commons.lang3.StringUtils;

import com.ctem.entity.UserEntity;

/**
 * @author Shashank
 *
 */
public class JwtAuthenticationResponse {

	private String accessToken;
	private String tokenType = "Basic";
	private UserEntity user;

	/**
	 * @param accessToken
	 * @param user
	 */
	public JwtAuthenticationResponse(String accessToken, UserEntity user) {
		super();
		this.accessToken = accessToken;
		if(StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(null);
		}
		this.user = user;
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

}
