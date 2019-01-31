package com.spring.project.manager;

import com.spring.project.entity.Employee;

import java.util.LinkedHashMap;
import java.util.List;

public interface UserManager {

    String greetUser(String name);

    List<Employee> findUserByFirstName(String firstName);

    Employee findByEmiratesIDNumber(String emiratesIDNumber);

    LinkedHashMap<String, Object> findAllUsers();

}