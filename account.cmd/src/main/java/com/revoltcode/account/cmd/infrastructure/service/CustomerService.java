package com.revoltcode.account.cmd.infrastructure.service;

import com.revoltcode.account.common.dto.rest.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feignCustomerService", url = "http://localhost:8080/api/v1/customers")
public interface CustomerService {

    @GetMapping("/{customerId}")
    ResponseEntity<Customer> getCustomer(@PathVariable("customerId") String customerId);
}
