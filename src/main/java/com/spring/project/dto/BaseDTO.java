package com.spring.project.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable {

    private Long ID;
    private String createdDate;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}