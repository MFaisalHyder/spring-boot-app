package com.spring.project.service;

import com.spring.project.entity.Role;
import com.spring.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrentUserDetails implements UserDetails {

    private static final Logger currentUserDetailsLogger = LogManager.getLogger(CurrentUserDetails.class);

    /**
     * <p><b>IMPORTANT<b/>
     * <br/>
     * If we are using ROLE_ prefix in DB which we should be, then while setting roles in @Override getAuthorities()
     * we do not need to append this ROLE_ prefix as DB entry already has it.
     * <p/>
     */
    private String ROLE_PREFIX = "ROLE_";

    private Long userID;
    private String emiratesID;
    private String firstName;
    private String lastName;
    private String staffID;
    private String email;
    private String password;
    private Role role;

    private CurrentUserDetails(Long ID, String emiratesID, String firstName, String lastName, String staffID, String email,
                               String password, Role role) {

        super();
        this.userID = ID;
        this.emiratesID = emiratesID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.staffID = staffID;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public Long getUserID() {
        return userID;
    }

    public String getEmiratesID() {
        return emiratesID;
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

    public Role getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();

        grantedAuthority.add(new SimpleGrantedAuthority(role.getName()));

        return grantedAuthority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Helper method to add all details of Current User into Security User Object
     *
     * @param user User
     * @return UserDetails
     */
    public static UserDetails create(User user) {
        return new CurrentUserDetails(user.getID(), user.getEmiratesID(), user.getFirstName(),
                user.getLastName(), user.getStaffID(), user.getEmail(), user.getPassword(), user.getRole());
    }

}