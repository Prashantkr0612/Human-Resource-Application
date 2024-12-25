package com.cg.humanresource.exception;

public class DepartmentNotFoundException extends RuntimeException{
	
	private String message;


	public DepartmentNotFoundException() {}
	public DepartmentNotFoundException(String message) {
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
		return "DepartmentNotFoundException [message=" + message + "]";
	}
	
}
