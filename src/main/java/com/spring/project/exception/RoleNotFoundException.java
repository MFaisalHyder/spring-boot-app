package com.spring.project.exception;

public class RoleNotFoundException extends BaseException {

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(String message, String detail) {
        super(message, detail);
    }

}