package com.cg.humanresource.exception;
 
public class ValidationException extends RuntimeException{
	private String message;
	public ValidationException() {}
	public ValidationException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Validation Failed";
	}
}
 
