package com.user.register_user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends RuntimeException{
    private String message;
    public RessourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public RessourceNotFoundException(){

    }

}
