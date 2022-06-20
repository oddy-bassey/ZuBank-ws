package com.revoltcode.account.cmd.infrastructure.service;

import com.revoltcode.account.common.dto.AccountType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feignAccountService", url = "http://localhost:8080/api/v1/accountLookup")
public interface AccountService {

    @GetMapping("/byCustomerAndAccountType/{accountId}/{accountType}")
    ResponseEntity<?> getAccountByCustomerAndAccountType(@PathVariable("accountId") String accountId,
                                  @PathVariable("accountType") AccountType accountType);
}
