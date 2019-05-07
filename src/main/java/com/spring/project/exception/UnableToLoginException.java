package com.spring.project.exception;

public class UnableToLoginException extends BaseException {

    public UnableToLoginException(String message) {
        super(message);
    }

    public UnableToLoginException(String message, String detail) {
        super(message, detail);
    }

}