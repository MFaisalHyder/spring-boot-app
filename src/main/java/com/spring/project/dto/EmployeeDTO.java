package com.spring.project.dto;

import java.io.Serializable;

public class EmployeeDTO extends BaseDTO implements Serializable {

    private String emiratesID;
    private String firstName;
    private String lastName;

    public String getEmiratesID() {
        return emiratesID;
    }

    public void setEmiratesID(String emiratesID) {
        this.emiratesID = emiratesID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}