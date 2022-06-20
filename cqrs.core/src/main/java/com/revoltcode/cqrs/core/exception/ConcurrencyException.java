package com.revoltcode.cqrs.core.exception;

public class ConcurrencyException extends RuntimeException{

    public  ConcurrencyException(String message){
        super(message);
    }
}
