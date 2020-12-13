package com.ctem.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Arvind Maurya
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3221500751623215836L;

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
