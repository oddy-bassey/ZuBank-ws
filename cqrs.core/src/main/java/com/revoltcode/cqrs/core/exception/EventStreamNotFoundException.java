package com.revoltcode.cqrs.core.exception;

public class EventStreamNotFoundException extends IllegalStateException {

    public EventStreamNotFoundException(String message){
        super(message);
    }
}
