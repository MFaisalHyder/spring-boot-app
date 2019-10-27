package com.spring.project.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class Response implements Serializable {

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
    private String details;

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

    public static Response prepareErrorResponse(HttpStatus status, String errorCode, String errorMessage) {

        return new ResponseBuilder()
                .setStatus(status)
                .setErrorCode(errorCode)
                .setMessage(errorMessage)
                .build();
    }

}