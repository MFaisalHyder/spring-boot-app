package com.spring.project.exception;

public class BaseException extends RuntimeException {

    private String message;
    private String detail;

    BaseException(String message) {
        this.message = message;
    }

    BaseException(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}