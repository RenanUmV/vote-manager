package com.manager.votemanager.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestExcpetion extends RuntimeException{

    public InvalidRequestExcpetion(String message){

        super(message);
    }
}
