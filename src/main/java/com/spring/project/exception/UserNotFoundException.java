package com.spring.project.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, String detail) {
        super(message, detail);
    }

}