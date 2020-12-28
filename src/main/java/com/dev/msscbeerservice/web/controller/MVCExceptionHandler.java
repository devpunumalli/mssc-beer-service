package com.dev.msscbeerservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MVCExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> handleErrors(ConstraintViolationException e) {
        List<String> errors = new ArrayList<String>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(error -> errors.add(error.toString()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<List> handleBindException(BindException e) {

        return new ResponseEntity<>(e.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
