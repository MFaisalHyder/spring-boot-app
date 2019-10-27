package com.spring.project.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserDTO extends BaseDTO {

    private String emiratesID;
    private String firstName;
    private String lastName;
    private String email;
    private String staffID;
    private String password;
    private RoleDTO role;

}