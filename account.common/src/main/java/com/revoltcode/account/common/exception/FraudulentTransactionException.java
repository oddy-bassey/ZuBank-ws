package com.revoltcode.account.common.exception;

public class FraudulentTransactionException extends IllegalStateException {

    public FraudulentTransactionException(String message){
        super(message);
    }
}
