package com.spring.project.controller;

import com.spring.project.entity.Employee;
import com.spring.project.exception.UserNotFoundException;
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

    private static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUsersList() {
        logger.info("UserController.getUsersList()[/listAll] :: method call ---- STARTS");

        LinkedHashMap<String, Object> result = userManager.findAllUsers();

        logger.info("UserController.getUsersList()[/listAll] :: method call ---- ENDS");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{emiratesID}", method = RequestMethod.GET)
    public ResponseEntity<Object> findUserByEmiratesID(@PathVariable(value = "emiratesID") String emiratesID) {
        logger.info("UserController.findUserByEmiratesID()[/{emiratesID}] :: method call ---- STARTS");

        if (StringUtils.isEmpty(emiratesID)) {
            throw new UserNotFoundException();
        }

        Employee employee = userManager.findByEmiratesIDNumber(emiratesID);

        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

}