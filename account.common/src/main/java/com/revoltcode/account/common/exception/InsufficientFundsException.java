package com.revoltcode.account.common.exception;

public class InsufficientFundsException extends IllegalStateException {

    public InsufficientFundsException(String message){
        super(message);
    }
}
