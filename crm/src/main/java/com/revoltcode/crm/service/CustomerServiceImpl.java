package com.revoltcode.crm.service;

import com.revoltcode.crm.model.Customer;
import com.revoltcode.crm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public long getCountOfCustomers() {
        return customerRepository.count();
    }

    @Override
    public Optional<Customer> findByCustomerId(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Optional<Customer> findByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
}
