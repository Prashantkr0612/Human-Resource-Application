package com.cg.humanresource.exception;
public class ValidationNotFoundException extends RuntimeException{
	private String message;
	public ValidationNotFoundException() {}
	public ValidationNotFoundException(String message) {
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