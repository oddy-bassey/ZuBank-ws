package com.revoltcode.account.common.exception;

public class NegativeDepositAmountException extends IllegalStateException {

    public NegativeDepositAmountException(String message){
        super(message);
    }
}
