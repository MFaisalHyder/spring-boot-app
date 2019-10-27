package com.spring.project.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
class BaseDTO implements Serializable {

    private Long ID;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;

}