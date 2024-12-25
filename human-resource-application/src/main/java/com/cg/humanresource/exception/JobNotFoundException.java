package com.cg.humanresource.exception;

public class JobNotFoundException extends Exception {

	private String message;

	public JobNotFoundException() {}
	public JobNotFoundException(String message) {
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
		return "JobNotFoundException [message=" + message + "]";
	}

}
