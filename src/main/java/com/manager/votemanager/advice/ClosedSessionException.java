package com.manager.votemanager.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class ClosedSessionException extends RuntimeException{

    public ClosedSessionException(String message){

        super(message);
    }
}
