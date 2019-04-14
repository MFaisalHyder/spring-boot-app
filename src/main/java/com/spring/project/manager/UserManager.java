package com.spring.project.manager;

import com.spring.project.dto.EmployeeDTO;
import com.spring.project.entity.Employee;
import com.spring.project.exception.InvalidInputException;
import com.spring.project.exception.UserNotFoundException;
import com.spring.project.exception.UserNotRegisteredException;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;

public interface UserManager {

    String greetUser(String name);

    List<EmployeeDTO> findUserByFirstName(String firstName) throws UserNotFoundException;

    EmployeeDTO findByEmiratesIDNumber(String emiratesIDNumber) throws UserNotFoundException;

    LinkedHashMap<String, Object> findAllUsers() throws UserNotFoundException;

    EmployeeDTO registerUser(EmployeeDTO employee) throws UserNotRegisteredException;

    EmployeeDTO loginUser(Employee employee) throws UserNotFoundException;

}