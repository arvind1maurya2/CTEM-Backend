package com.ctem.payload;
public class ApiResponse {

	private Boolean success;
	private String message;
	private Long id;

	/**
	 * @param success
	 * @param message
	 */
	public ApiResponse(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	

	/**
	 * @param success
	 * @param message
	 * @param id
	 */
	public ApiResponse(Boolean success, String message, Long id) {
		super();
		this.success = success;
		this.message = message;
		this.id = id;
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



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
}
