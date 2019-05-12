package com.spring.project.controller;

import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.dto.LoginDTO;
import com.spring.project.dto.UserDTO;
import com.spring.project.exception.InvalidInputException;
import com.spring.project.manager.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ApplicationController {

    private static Logger applicationControllerLogger = LogManager.getLogger(ApplicationController.class);

    private final UserManager userManager;
    private final PropertiesConfig propertiesConfig;

    @Autowired
    public ApplicationController(final UserManager userManager, final PropertiesConfig propertiesConfig) {
        this.userManager = userManager;
        this.propertiesConfig = propertiesConfig;

    }

    @RequestMapping(value = {"/", "/greetUser"}, method = RequestMethod.GET)
    public ResponseEntity<String> greetUser() {
        applicationControllerLogger.info("ApplicationController.greetUser()[/greetUser] :: method call ---- STARTS");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth != null ? auth.getName() : ApplicationConstants.GeneralConstants.GUEST.getValue();

        String greetingMessage = userManager.greetUser(name);

        applicationControllerLogger.info("ApplicationController.greetUser()[/greetUser] :: method call ---- ENDS");

        return new ResponseEntity<>(greetingMessage, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO user) {
        applicationControllerLogger.info("ApplicationController.registerUser()[/register] :: method call ---- STARTS");

        if (user == null) {
            throw new InvalidInputException(ApplicationConstants.ErrorCodes.INVALID_PARAMETER.getValue(),
                    propertiesConfig.getProperty(ApplicationProperties.Messages.INVALID_USER_DATA.getValue()));
        }

        UserDTO registeredUser = userManager.registerUser(user);

        applicationControllerLogger.info("ApplicationController.registerUser()[/register] :: method call ---- ENDS");

        return new ResponseEntity<>(registeredUser, HttpStatus.OK);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginDTO> loginUser(HttpServletRequest httpServletRequest, @RequestBody UserDTO user) {
        applicationControllerLogger.info("ApplicationController.loginUser()[/login] :: method call ---- STARTS");

        if (user == null || StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPassword())) {

            throw new InvalidInputException(ApplicationConstants.ErrorCodes.INVALID_PARAMETER.getValue(),
                    propertiesConfig.getProperty(ApplicationProperties.Messages.INVALID_USER_DATA.getValue()));
        }

        applicationControllerLogger.info("ApplicationController.loginUser()[/login] :: method call ---- ENDS");

        LoginDTO loginResponse = userManager.loginUser(user);

        return new ResponseEntity<>(loginResponse, addCsrfTokenInHeader(httpServletRequest), HttpStatus.OK);
    }

    /**
     * <b>IMPORTANT<b/>
     * <br/>
     * <p>
     * <p>
     * We can expose an end point for CSRF token for requests other than GET for our application.
     * <br/>
     * But it is better to ad token in response header when User logs in. As this approach is vulnerable to some extent.
     * <br/>
     * Like, even if we have Same Origin Policy enforced, this end point just requires an authorized User's credentials.
     * <p/>
     *
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<String> csrfToken
     */
    @Deprecated
    @RequestMapping(value = "/csrf-token", method = RequestMethod.GET)
    public ResponseEntity<String> getCsrfToken(HttpServletRequest httpServletRequest) {
        applicationControllerLogger.info("ApplicationController.getCsrfToken()[/csrf-token] :: method call ---- STARTS");

        CsrfToken csrfToken = (CsrfToken) httpServletRequest.getAttribute(CsrfToken.class.getName());

        applicationControllerLogger.info("ApplicationController.getCsrfToken()[/csrf-token] :: method call ---- ENDS");

        return new ResponseEntity<>(csrfToken.getToken(), HttpStatus.OK);
    }

    private HttpHeaders addCsrfTokenInHeader(HttpServletRequest httpServletRequest) {
        CsrfToken csrfToken = (CsrfToken) httpServletRequest.getAttribute(CsrfToken.class.getName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(csrfToken.getHeaderName(), csrfToken.getToken());

        return httpHeaders;
    }

}