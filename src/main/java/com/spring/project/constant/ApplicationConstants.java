package com.spring.project.constant;

public class ApplicationConstants {

    /**
     * General constants used across application
     */
    public enum GeneralConstants {
        SERVICE_ISSUE("We cannot process your request at this time."),
        SUCCESS("Success"),
        FAILED("Failed"),
        ZERO_RECORDS("No records found"),
        EMPTY_STRING(""),
        MISSING_PARAMETER("Required parameter is missing"),
        WELCOME("Welcome, "),
        GUEST("GUEST");

        private String value;

        GeneralConstants(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum HomePageConstants {
        USER_NAME_NOT_FOUND("User name not found");

        private String value;

        HomePageConstants(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ErrorCodes {
        MISSING_PARAMETER("MISSING_PARAMETER"),
        ZERO_RECORD("ZERO_RECORD"),
        GENERAL_ERROR("500-GENERAL_ERROR"),
        INVALID_PARAMETER("INVALID_PARAMETER"),
        USER_NOT_REGISTERED("USER_NOT_REGISTERED"),
        LOGIN_FAILED("LOGIN_FAILED");

        private String value;

        ErrorCodes(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH-mm-ss";

    public enum CSRF_HEADERS {
        REQUEST_ATTRIBUTE_NAME("_csrf"),
        RESPONSE_HEADER_NAME("X-CSRF-HEADER"),
        RESPONSE_PARAM_NAME("X-CSRF-PARAM"),
        RESPONSE_TOKEN_NAME("X-CSRF-TOKEN");

        private String header;

        CSRF_HEADERS(String headerValue) {
            this.header = headerValue;
        }

        public String getValue() {
            return header;
        }

    }

}