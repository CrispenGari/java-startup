package com.errors.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

//    Handling specific exception

    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<?> animalNotFound(AnimalNotFoundException exception, WebRequest request){
        ErrorType errorType = new ErrorType(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorType, HttpStatus.NOT_FOUND);
    }

//    Global exception handling
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
        return new ResponseEntity<>(
                new ErrorType(new Date(), exception.getMessage(), request.getDescription(false))
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
