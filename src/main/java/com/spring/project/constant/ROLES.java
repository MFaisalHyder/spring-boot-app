package com.spring.project.constant;

public enum ROLES {
    PREFIX("ROLE_"),
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    ROLES(String role) {
        this.role = role;
    }

    public String getValue() {
        return this.role;
    }

}