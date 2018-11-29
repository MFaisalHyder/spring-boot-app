package com.spring.project.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class Response implements Serializable {

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
    private String details;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public static final class ResponseBuilder {
        private HttpStatus httpStatus;
        private String errorCode;
        private String message;
        private String details;

        public ResponseBuilder() {
        }

        public ResponseBuilder setStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;

            return this;
        }

        public ResponseBuilder setErrorCode(String errorCode) {
            this.errorCode = errorCode;

            return this;
        }

        public ResponseBuilder setMessage(String message) {
            this.message = message;

            return this;
        }

        public ResponseBuilder setDetails(String details) {
            this.details = details;

            return this;
        }

        public Response build() {
            Response response = new Response();

            response.httpStatus = this.httpStatus;
            response.errorCode = this.errorCode;
            response.message = this.message;
            response.details = this.details;

            return response;
        }

    }

}