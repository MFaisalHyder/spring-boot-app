package com.spring.project.controller;

import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.dto.EmployeeDTO;
import com.spring.project.entity.Employee;
import com.spring.project.exception.InvalidInputException;
import com.spring.project.manager.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static Logger userControllerLogger = LogManager.getLogger(UserController.class);

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTO> registerUser(@RequestBody EmployeeDTO employee) {
        userControllerLogger.info("UserController.registerUser()[/register] :: method call ---- STARTS");

        if (employee == null) {
            throw new InvalidInputException(ApplicationConstants.ErrorCodes.INVALID_PARAMETER.getValue(),
                    propertiesConfig.getProperty(ApplicationProperties.Messages.INVALID_USER_DATA.getValue()));
        }

        EmployeeDTO registeredEmployee = userManager.registerUser(employee);

        userControllerLogger.info("UserController.registerUser()[/register] :: method call ---- ENDS");

        return new ResponseEntity<>(registeredEmployee, HttpStatus.OK);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> loginUser(Employee employee) {
        userControllerLogger.info("UserController.loginUser()[/login] :: method call ---- STARTS");

        if (employee == null) {
            throw new InvalidInputException(ApplicationConstants.ErrorCodes.INVALID_PARAMETER.getValue(),
                    propertiesConfig.getProperty(ApplicationProperties.Messages.INVALID_USER_DATA.getValue()));
        }

        userControllerLogger.info("UserController.loginUser()[/login] :: method call ---- ENDS");

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUsersList() {
        userControllerLogger.info("UserController.getUsersList()[/listAll] :: method call ---- STARTS");

        LinkedHashMap<String, Object> result = userManager.findAllUsers();

        userControllerLogger.info("UserController.getUsersList()[/listAll] :: method call ---- ENDS");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/emiratesID/{emiratesID}", method = RequestMethod.GET)
    public ResponseEntity<Object> findUserByEmiratesID(@PathVariable(value = "emiratesID") String emiratesID) {
        userControllerLogger.info("UserController.findUserByEmiratesID()[/emiratesID] :: method call ---- STARTS");

        if (StringUtils.isEmpty(emiratesID)) {
            userControllerLogger.error("UserController.findUserByEmiratesID()[/emiratesID/] :: EmiratesID is unavailable = '{}'", emiratesID);

            throw new InvalidInputException(propertiesConfig.getProperty(ApplicationProperties.Messages.PARAMETER_MISSING.getValue())
                    , propertiesConfig.getProperty(ApplicationProperties.Messages.PARAMETER_MISSING_EMIRATES_ID.getValue()));
        }

        EmployeeDTO employee = userManager.findByEmiratesIDNumber(emiratesID);

        userControllerLogger.info("UserController.findUserByEmiratesID()[/emiratesID/] :: method call ---- ENDS");

        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

}