package com.spring.project.exception;

public class UserNotFoundException extends RuntimeException {

    /**
     * We can add parameters in our Exception if we want to add particular messages for a given Exception instead of a
     * single general response from ExceptionHandlerClass
     */

    private String message;

    public UserNotFoundException(String message) {
        this.message = message;

    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}