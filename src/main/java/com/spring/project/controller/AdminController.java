package com.spring.project.controller;

import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.dto.UserDTO;
import com.spring.project.manager.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private static final Logger adminControllerLogger = LogManager.getLogger(AdminController.class);

    private final PropertiesConfig propertiesConfig;
    private final UserManager userManager;

    @Autowired
    public AdminController(final PropertiesConfig propertiesConfig, final UserManager userManager) {
        this.propertiesConfig = propertiesConfig;
        this.userManager = userManager;

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ResponseEntity<String> adminPortal() {
        adminControllerLogger.info("AdminController.adminPortal()[/home] :: method call ---- STARTS");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDTO adminUser = userManager.findUserByEmail(auth.getName());

        if (adminUser == null) {
            throw new UsernameNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.NO_USER_FOUND.getValue()));
        }

        adminControllerLogger.info("AdminController.adminPortal()[/home] :: method call ---- ENDS");

        return new ResponseEntity<>(ApplicationConstants.GeneralConstants.WELCOME.getValue() + adminUser.getStaffID(), HttpStatus.OK);

    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ResponseEntity<String> adminTasks() {
        adminControllerLogger.info("UserController.getUsersList()[/tasks] :: method call ---- STARTS");

        adminControllerLogger.info("UserController.getUsersList()[/tasks] :: method call ---- ENDS");

        return new ResponseEntity<>("This is ADMIN", HttpStatus.OK);
    }
}