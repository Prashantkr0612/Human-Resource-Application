package com.cg.humanresource.exception;

public class LocationNotFoundException extends RuntimeException {

    private String message;

    public LocationNotFoundException() {}

    public LocationNotFoundException(String message) {
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
        return "LocationNotFoundException [message=" + message + "]";
    }
}
