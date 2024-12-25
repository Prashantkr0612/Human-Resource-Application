package com.cg.humanresource.exception;


public class UserNotFoundException extends RuntimeException {
    private String message;

    // Default constructor
    public UserNotFoundException() {}

    // Parameterized constructor
    public UserNotFoundException(String message) {
        super(message); // Call the superclass constructor to set the message
        this.message = message;
    }

    // Getter for message
    public synchronized String getMessage() {
        return message;
    }

    // Setter for message
    public synchronized void setMessage(String message) {
        this.message = message;
    }

    // Override toString for custom representation
    @Override
    public String toString() {
        return "UserNotFoundException [message=" + message + "]";
    }
}
