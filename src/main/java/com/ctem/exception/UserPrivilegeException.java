package com.ctem.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Arvind Maurya
 *
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserPrivilegeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserPrivilegeException(String message) {
		super(message);
	}

	public UserPrivilegeException(String message, Throwable cause) {
		super(message, cause);
	}
}
