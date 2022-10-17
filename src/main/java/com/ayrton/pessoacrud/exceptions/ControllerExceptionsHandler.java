package com.ayrton.pessoacrud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionsHandler {


    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErroPadrao> unsupported(UnsupportedOperationException e, HttpServletRequest request) {
        Integer status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        return ResponseEntity.status(status).body(new ErroPadrao(status,e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErroPadrao> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        Integer status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new ErroPadrao(status,e.getMessage()));
    }
}
