package com.revoltcode.account.query.infrastructure.service;

import com.revoltcode.account.common.dto.rest.Transaction;
import com.revoltcode.account.common.event.transaction.TransactionEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "feignTransactionService", url = "http://${feign.transaction.hostname}:8080/api/v1/transactions")
public interface TransactionService {

    @PostMapping("/")
    ResponseEntity<String> postTransaction(@RequestBody TransactionEvent transaction);

    @GetMapping("/byAccountId/{accountId}")
    ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable("accountId") String accountId);
}
