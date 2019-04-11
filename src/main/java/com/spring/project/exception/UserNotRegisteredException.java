package com.spring.project.exception;

public class UserNotRegisteredException extends BaseException {

    public UserNotRegisteredException(String message) {
        super(message);
    }

    public UserNotRegisteredException(String message, String detail) {
        super(message, detail);
    }

}