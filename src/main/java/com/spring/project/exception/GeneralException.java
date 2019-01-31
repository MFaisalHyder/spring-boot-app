package com.spring.project.exception;

public class GeneralException extends BaseException {

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, String detail) {
        super(message, detail);
    }

}