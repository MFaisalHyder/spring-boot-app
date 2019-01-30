package com.spring.project.config;

import com.spring.project.dto.Response;
import com.spring.project.exception.UserNotFoundException;
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
    public ResponseEntity<Response> userNotFoundExceptionHandler(UserNotFoundException userNotFoundException) {
        Response exceptionResponse = new Response.ResponseBuilder()
                .setStatus(HttpStatus.NOT_FOUND)
                .setErrorCode("UNF")
                .setMessage(userNotFoundException.getMessage())
                .setDetails(userNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

}