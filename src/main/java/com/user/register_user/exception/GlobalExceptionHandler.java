package com.user.register_user.exception;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
    @Value(value = "${data.exception.message1}")
    private String message1;

    @Value(value = "${data.exception.message2}")
    private String message2;

    @Value(value = "${data.exception.message3}")
    private String message3;

    //handling specific exception
    @ExceptionHandler(RessourceNotFoundException.class)
    public final ResponseEntity<ErrorDetails> ressourceNotFoundException(RessourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message2,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RessourceAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> ressourceAlreadyExistsException (RessourceAlreadyExistsException ex,  WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message1,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> dataBaseConnectionFailsException(Exception exception,  WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message3,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }




}



