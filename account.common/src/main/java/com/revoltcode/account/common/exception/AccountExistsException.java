package com.revoltcode.account.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountExistsException extends RuntimeException{

    public AccountExistsException(String message){
        super(message);
    }
}
