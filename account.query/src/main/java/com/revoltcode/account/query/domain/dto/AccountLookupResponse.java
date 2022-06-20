package com.revoltcode.account.query.domain.dto;

import com.revoltcode.account.common.dto.BaseResponse;
import com.revoltcode.account.query.domain.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountLookupResponse(String message){
        super(message);
    }
}
