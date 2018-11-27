package com.spring.project.service;

import com.spring.project.Application;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.entity.Employee;
import com.spring.project.manager.HomePageManager;
import com.spring.project.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class HomePageService implements HomePageManager {

    private static final Logger homePageServiceLogger = LogManager.getLogger(HomePageService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public String greetUser(String name) {
        if (StringUtils.isEmpty(name)) {
            return ApplicationConstants.HomePageConstants.USER_NAME_NOT_FOUND.getValue();
        }

        return "Welcome, " + name;
    }

    @Override
    public List<Employee> findUserByFirstName(String firstName) {
        List<Employee> usersFoundFromDB = new ArrayList<>();

        if (StringUtils.isEmpty(firstName)) {
            return null;
        }

        try {
            usersFoundFromDB = userRepository.findByFirstName(firstName);

        } catch (Exception exception) {
            System.out.println("findUserByFirstName() :: FAILED" + exception);
        }

        return usersFoundFromDB;
    }

    @Override
    public List<Employee> findUserByLastName(String lastName) {
        List<Employee> usersFoundFromDB = new ArrayList<>();

        if (StringUtils.isEmpty(lastName)) {
            return null;
        }

        try {
            usersFoundFromDB = userRepository.findByLastName(lastName);

        } catch (Exception exception) {
            System.out.println("findUserByLastName() :: FAILED" + exception);
        }

        return usersFoundFromDB;
    }

    @Override
    public Employee findByEmiratesIDNumber(String emiratesIDNumber) {
        homePageServiceLogger.info("HomePageService.findByEmiratesIDNumber() :: method call ---- STARTS");

        Employee userFoundFromDB = null;

        if (StringUtils.isEmpty(emiratesIDNumber)) {
            return null;
        }

        try {
            userFoundFromDB = userRepository.findByEmiratesID(emiratesIDNumber);

        } catch (Exception exception) {
            System.out.println("findByEmiratesIDNumber() :: FAILED" + exception);
        }


        homePageServiceLogger.info("HomePageService.findByEmiratesIDNumber() :: method call ---- ENDS");

        return userFoundFromDB;
    }

    @Override
    public LinkedHashMap<String, Object> findAllUsers() {
        homePageServiceLogger.info("HomePageService.findAllUsers() :: method call ---- STARTS");

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        List<Employee> allUsersInDB = null;
        String status;

        try {
            allUsersInDB = userRepository.findAll();

            status = allUsersInDB != null && allUsersInDB.size() > 0
                    ? ApplicationConstants.GeneralConstants.SUCCESS.getValue()
                    : ApplicationConstants.GeneralConstants.ZERO_RECORDS.getValue();

        } catch (Exception exception) {
            homePageServiceLogger.error("HomePageService.findAllUsers() :: unable to find all users list ---- FAILED \n" + exception);

            status = ApplicationConstants.GeneralConstants.FAILED.getValue();
        }

        result.put("status", status);
        result.put("usersList", allUsersInDB);

        homePageServiceLogger.info("HomePageService.findAllUsers() :: method call ---- ENDS");

        return result;
    }

}