package com.testtask.demo.controller;


import com.testtask.demo.exception.NoPurchaseInDatabaseException;
import com.testtask.demo.exception.WrongDataException;
import com.testtask.demo.wire.ExceptionWire;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ExceptionWire> handleResourceNotFoundException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(NoPurchaseInDatabaseException.class)
    public ResponseEntity<ExceptionWire> notFoundException(NoPurchaseInDatabaseException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ExceptionWire(HttpStatus.NOT_FOUND.toString(), ex.getMessage()));
    }

    @ExceptionHandler(value = {WrongDataException.class})
    public ResponseEntity<ExceptionWire> handleWrongDataException(WrongDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

}
