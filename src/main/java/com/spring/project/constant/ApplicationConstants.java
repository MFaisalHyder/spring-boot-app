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
        MISSING_PARAMETER("Required parameter is missing");

        private String value;

        GeneralConstants(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum HomePageConstants {
        USER_NAME_NOT_FOUND("Employee name not found");

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
        GENERAL_ERROR("500-GENERAL_ERROR");

        private String value;

        ErrorCodes(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}