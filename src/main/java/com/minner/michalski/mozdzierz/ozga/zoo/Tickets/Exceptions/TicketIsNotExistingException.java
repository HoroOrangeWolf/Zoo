package com.minner.michalski.mozdzierz.ozga.zoo.Tickets.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(BAD_REQUEST)
public class TicketIsNotExistingException extends RuntimeException{
    public TicketIsNotExistingException(String message){
        super(message);
    }
}
