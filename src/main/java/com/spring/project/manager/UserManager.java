package com.spring.project.manager;

import com.spring.project.dto.LoginDTO;
import com.spring.project.dto.UserDTO;
import com.spring.project.exception.UnableToLoginException;
import com.spring.project.exception.UserNotFoundException;
import com.spring.project.exception.UserNotRegisteredException;

import java.util.LinkedHashMap;
import java.util.List;

public interface UserManager {

    String greetUser(String name);

    List<UserDTO> findUserByFirstName(String firstName) throws UserNotFoundException;

    UserDTO findUserByEmiratesID(String emiratesIDNumber) throws UserNotFoundException;

    LinkedHashMap<String, Object> findAllUsers() throws UserNotFoundException;

    UserDTO registerUser(UserDTO employee) throws UserNotRegisteredException;

    LoginDTO loginUser(UserDTO user) throws UserNotFoundException, UnableToLoginException;

    List<UserDTO> findUserByLastName(String lastName) throws UserNotFoundException;

    UserDTO findUserByStaffID(String staffID) throws UserNotFoundException;

    UserDTO findUserByEmail(String email) throws UserNotFoundException;

}