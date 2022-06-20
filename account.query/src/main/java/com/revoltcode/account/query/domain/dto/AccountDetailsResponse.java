package com.revoltcode.account.query.domain.dto;

import com.revoltcode.account.common.dto.BaseResponse;
import com.revoltcode.account.common.dto.rest.Customer;
import com.revoltcode.account.common.dto.rest.Transaction;
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
public class AccountDetailsResponse extends BaseResponse {
    private Customer customerInfo;
    private BankAccount accountInfo;
    private List<Transaction> accountTransactions;
}
