package com.cg.humanresource.exception;
 
public class RegionNotFoundException extends RuntimeException {
	private String message;
	
 
    public RegionNotFoundException() {}
	public RegionNotFoundException(String message) {
    	this.message = message;
    }
    public String getMessage() {
		return message;
	}
	
	public String toString() {
		return "com.cg.humanresuorce.exception.RegionsNotFoundException : " + message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}