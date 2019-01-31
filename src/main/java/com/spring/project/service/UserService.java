package com.spring.project.service;

import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.entity.Employee;
import com.spring.project.exception.GeneralException;
import com.spring.project.exception.InvalidInputException;
import com.spring.project.exception.UserNotFoundException;
import com.spring.project.manager.UserManager;
import com.spring.project.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.java2d.loops.FillRect;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class UserService implements UserManager {

    private static final Logger userServiceLogger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public String greetUser(String name) {
        userServiceLogger.info("UserService.greetUser() :: method call ---- STARTS");

        if (StringUtils.isEmpty(name)) {
            userServiceLogger.error("UserService.greetUser() :: User name is unavailable = '{}'", name);

            throw new InvalidInputException("User name is missing = " + name);
        }

        userServiceLogger.info("UserService.greetUser() :: method call ---- ENDS");

        return "Welcome, " + name;
    }

    @Override
    public List<Employee> findUserByFirstName(String firstName) {
        userServiceLogger.info("UserService.findUserByFirstName() :: method call ---- STARTS");

        List<Employee> usersFoundFromDB;

        if (StringUtils.isEmpty(firstName)) {
            userServiceLogger.error("UserService.findUserByFirstName() :: User first name is unavailable = '{}'", firstName);

            throw new InvalidInputException("User first name is missing = " + firstName);
        }

        try {
            usersFoundFromDB = userRepository.findByFirstName(firstName);

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findUserByFirstName() ::  Some error occurred while finding user");
            userServiceLogger.error(exception);

            throw new GeneralException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()));
        }

        userServiceLogger.info("UserService.findUserByFirstName() :: method call ---- ENDS");

        return usersFoundFromDB;
    }

    @Override
    public Employee findByEmiratesIDNumber(String emiratesID) {
        userServiceLogger.info("UserService.findByEmiratesID() :: method call ---- STARTS");

        Employee userFoundFromDB;

        try {
            userFoundFromDB = userRepository.findByEmiratesID(emiratesID);

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findByEmiratesIDNumber() ::  Some error occurred while finding user");
            userServiceLogger.error(exception);

            throw new GeneralException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()),
                    exception.getMessage());

        }

        if (userFoundFromDB == null) {
            userServiceLogger.error("User not found for given EmiratesID = " + emiratesID);

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.NO_USER_FOUND.getValue()) + " for EmiratesID = " + emiratesID
                    , propertiesConfig.getProperty(ApplicationProperties.Messages.NO_USER_FOUND_EMIRATES_ID.getValue()));
        }

        userServiceLogger.info("UserService.findByEmiratesID() :: method call ---- ENDS");

        return userFoundFromDB;
    }

    @Override
    public LinkedHashMap<String, Object> findAllUsers() {
        userServiceLogger.info("UserService.findAllUsers() :: method call ---- STARTS");

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        List<Employee> allUsersInDB;
        String status;

        try {
            allUsersInDB = userRepository.findAll();

            status = allUsersInDB != null && allUsersInDB.size() > 0
                    ? ApplicationConstants.GeneralConstants.SUCCESS.getValue()
                    : ApplicationConstants.GeneralConstants.ZERO_RECORDS.getValue();

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findAllUsers() :: Some error occurred while finding users list");
            userServiceLogger.error(exception);

            throw new GeneralException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER_LIST.getValue()));

        }

        result.put("status", status);
        result.put("usersList", allUsersInDB);

        userServiceLogger.info("UserService.findAllUsers() :: method call ---- ENDS");

        return result;
    }

}
