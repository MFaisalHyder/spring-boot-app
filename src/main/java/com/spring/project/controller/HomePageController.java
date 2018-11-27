package com.spring.project.controller;

import com.spring.project.constant.ApplicationConstants;
import com.spring.project.entity.Employee;
import com.spring.project.manager.HomePageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class HomePageController {

    private static Logger homePageLogger = LogManager.getLogger(HomePageController.class);

    @Autowired
    private HomePageManager homePageManager;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUsersList() {
        homePageLogger.info("HomePageController.getUsersList()[/listAll] :: method call ---- STARTS");

        LinkedHashMap<String, Object> result = homePageManager.findAllUsers();

        homePageLogger.info("HomePageController.getUsersList()[/listAll] :: method call ---- ENDS");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}