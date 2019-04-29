package com.spring.project.constant;

public class ApplicationProperties {

    public enum Messages {
        PARAMETER_MISSING("required.parameter.missing"),
        PARAMETER_MISSING_EMIRATES_ID("required.parameter.missing.emirates.id"),
        UNABLE_TO_FIND_USER("unable.to.find.user"),
        NO_USER_FOUND("no.user.found"),
        UNABLE_TO_FIND_USER_LIST("unable.to.find.user.list"),
        NO_USER_FOUND_EMIRATES_ID("user.not.found.emirates.id"),
        INVALID_USER_DATA("user.invalid.data"),
        UNABLE_TO_REGISTER_USER("user.not.registered"),
        NO_ROLE_FOUND("no.role.found"),
        REQUEST_NOT_PROCESSED("request.not.processed");

        private String value;

        Messages(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

}