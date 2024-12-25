package com.cg.humanresource.exception;

public class ResourceNotFoundException extends RuntimeException {
	private String message;

    public ResourceNotFoundException() {}
    
    public ResourceNotFoundException(String message) {
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
        return "OpenJobPositionsNotFoundException [message=" + message + "]";
    }
}