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
        EMPTY_STRING("");

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
}