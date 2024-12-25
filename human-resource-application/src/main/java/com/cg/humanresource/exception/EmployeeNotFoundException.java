package com.cg.humanresource.exception;

public class EmployeeNotFoundException extends RuntimeException{
	
	private String message;

	public EmployeeNotFoundException() {}
	public EmployeeNotFoundException(String message) {
		super();
		this.message = message;
	}

	public synchronized String getMessage() {
		return message;
	}

	public synchronized void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "EmployeeNotFoundException [message=" + message + "]";
	}
	
	
}
