package com.spring.project.config;

import com.spring.project.constant.ApplicationConstants;
import com.spring.project.response.ExceptionResponse;
import com.spring.project.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void defaultExceptionHandler() {

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundExceptionHandler(UserNotFoundException userNotFoundException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.NOT_FOUND)
                .setCode(ApplicationConstants.ErrorCodes.ZERO_RECORD.getValue())
                .setMessage(userNotFoundException.getMessage())
                .setDetails(userNotFoundException.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ExceptionResponse> invalidInputExceptionHandler(InvalidInputException invalidInputException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setCode(ApplicationConstants.ErrorCodes.MISSING_PARAMETER.getValue())
                .setMessage(invalidInputException.getMessage())
                .setDetails(invalidInputException.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ExceptionResponse> generalExceptionHandler(GeneralException generalException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setCode(ApplicationConstants.ErrorCodes.GENERAL_ERROR.getValue())
                .setMessage(generalException.getMessage())
                .setDetails(generalException.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    public ResponseEntity<ExceptionResponse> userNotRegisteredExceptionHandler(UserNotRegisteredException userNotRegisteredException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setCode(ApplicationConstants.ErrorCodes.USER_NOT_REGISTERED.getValue())
                .setMessage(userNotRegisteredException.getMessage())
                .setDetails(userNotRegisteredException.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> roleNotFoundExceptionHandler(RoleNotFoundException roleNotFoundException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setCode(ApplicationConstants.ErrorCodes.ZERO_RECORD.getValue())
                .setMessage(roleNotFoundException.getMessage())
                .setDetails(roleNotFoundException.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(UnableToLoginException.class)
    public ResponseEntity<ExceptionResponse> unableToLoginExceptionHandler(UnableToLoginException unableToLoginException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setCode(ApplicationConstants.ErrorCodes.LOGIN_FAILED.getValue())
                .setMessage(unableToLoginException.getMessage())
                .setDetails(unableToLoginException.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

}