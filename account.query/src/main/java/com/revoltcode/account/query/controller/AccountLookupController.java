package com.revoltcode.account.query.controller;

import com.revoltcode.account.common.dto.AccountType;
import com.revoltcode.account.common.dto.BaseResponse;
import com.revoltcode.account.common.dto.rest.Customer;
import com.revoltcode.account.common.dto.rest.Transaction;
import com.revoltcode.account.query.domain.dto.AccountCount;
import com.revoltcode.account.query.domain.dto.AccountDetailsResponse;
import com.revoltcode.account.query.domain.model.BankAccount;
import com.revoltcode.account.query.domain.dto.AccountLookupResponse;
import com.revoltcode.account.query.infrastructure.service.CustomerService;
import com.revoltcode.account.query.infrastructure.service.TransactionService;
import com.revoltcode.account.query.query.*;
import com.revoltcode.cqrs.core.infrastructure.dispatcher.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accountLookup")
public class AccountLookupController {

    private final QueryDispatcher queryDispatcher;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @GetMapping("/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){

        List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
        if(accounts == null || accounts.size() <= 0) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        var response = AccountLookupResponse.builder()
                .accounts(accounts)
                .message(MessageFormat.format("Successfully returned {0} bank account(s)", accounts.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id){

        List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
        if(accounts == null || accounts.size() <= 0) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        var response = AccountLookupResponse.builder()
                .accounts(accounts)
                .message("Successfully returned bank account!")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/byCustomer/{customerId}")
    public ResponseEntity<AccountLookupResponse> getAccountByCustomerId(@PathVariable("customerId") String customerId){

        List<BankAccount> accounts = queryDispatcher.send(new FindAccountByCustomerIdQuery(customerId));
        if(accounts == null || accounts.size() <= 0) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        var response = AccountLookupResponse.builder()
                .accounts(accounts)
                .message("Successfully returned bank account!")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/byCustomerAndAccountType/{customerId}/{accountType}")
    public ResponseEntity<AccountLookupResponse> getAccountByCustomerAndAccountType(@PathVariable("customerId") String customerId,
                @PathVariable("accountType") AccountType accountType){

        List<BankAccount> accounts = queryDispatcher.send(new FindAccountByCustomerIdAndAccountTypeQuery(customerId, accountType));
        if(accounts == null || accounts.size() <= 0) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        var response = AccountLookupResponse.builder()
                .accounts(accounts)
                .message("Successfully returned bank account!")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details/{accountId}")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(@PathVariable("accountId") String accountId){
        BankAccount account = (BankAccount) queryDispatcher.send(new FindAccountByIdQuery(accountId)).get(0);
        Customer customer = customerService.getCustomer(account.getCustomerId()).getBody();
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(account.getId()).getBody();

        AccountDetailsResponse details = AccountDetailsResponse.builder()
                .accountInfo(account)
                .customerInfo(customer)
                .accountTransactions(transactions)
                .build();

        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCountOfAccounts(){
        AccountCount count = (AccountCount) queryDispatcher.send(new GetAccountCountQuery()).get(0);
        long numberOfAccounts = count.getNumberOfAccounts();
        return new ResponseEntity<>(numberOfAccounts, HttpStatus.OK);
    }
}
