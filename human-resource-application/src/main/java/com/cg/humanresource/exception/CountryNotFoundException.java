
package com.cg.humanresource.exception;

public class CountryNotFoundException  extends RuntimeException {
		private String message;
		
	 
	    public CountryNotFoundException() {}
		public CountryNotFoundException(String message) {
	    	this.message = message;
	    }
	    public String getMessage() {
			return message;
		}
		
		public String toString() {
			return "com.cg.humanresuorce.exception.CountryNotFoundException : " + message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}


