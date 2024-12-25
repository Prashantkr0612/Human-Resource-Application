package com.cg.humanresource.exception;

public class OpenJobPositionsNotFoundException extends Exception {

    private String message;

    public OpenJobPositionsNotFoundException() {}
    
    public OpenJobPositionsNotFoundException(String message) {
        super();
        this.message = message;
    }

    @Override
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
