package com.spring.project.controller;

import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.dto.UserDTO;
import com.spring.project.exception.InvalidInputException;
import com.spring.project.manager.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        UserDTO user = userManager.findUserByEmiratesID(emiratesID);

        userControllerLogger.info("UserController.findUserByEmiratesID()[/emiratesID/] :: method call ---- ENDS");

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

}