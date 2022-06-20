package com.revoltcode.cqrs.core.exception;

public class AggregateNotFoundException extends RuntimeException{

    public AggregateNotFoundException(String message){
        super(message);
    }
}
