package com.revoltcode.crm.service;

import com.revoltcode.crm.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    long getCountOfCustomers();
    Optional<Customer> findByCustomerId(UUID customerId);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    Customer save(Customer customer);
    void deleteCustomer(Customer customer);
}
