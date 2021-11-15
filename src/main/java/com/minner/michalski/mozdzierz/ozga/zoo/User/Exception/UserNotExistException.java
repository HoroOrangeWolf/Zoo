package com.minner.michalski.mozdzierz.ozga.zoo.User.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(NOT_FOUND)
public class UserNotExistException extends RuntimeException{

    public UserNotExistException(String message){
        super(message);
    }
}
