package com.spring.project.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends BaseEntity {

    @Column(name = "EmiratesID", unique = true, nullable = false, updatable = false)
    private String emiratesID;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "StaffID", unique = true, nullable = false, updatable = false)
    private String staffID;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

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

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}