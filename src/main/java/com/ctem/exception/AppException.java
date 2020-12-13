package com.ctem.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Arvind Maurya
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8216381748542763145L;

	public AppException(String message) {
		super(message);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

}
