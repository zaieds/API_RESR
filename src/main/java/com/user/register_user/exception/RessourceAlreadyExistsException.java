package com.user.register_user.exception;

public class RessourceAlreadyExistsException extends RuntimeException{

    private String message;

    public RessourceAlreadyExistsException(String message){
        super(message);
        this.message=message;

    }
    public RessourceAlreadyExistsException(){

    }

}
