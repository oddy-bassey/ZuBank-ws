package com.revoltcode.crm.controller;

import com.revoltcode.crm.model.Customer;
import com.revoltcode.crm.dto.CustomerDto;
import com.revoltcode.account.common.exception.CustomerNotFoundException;
import com.revoltcode.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<?> findAllCustomers(){
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable("id")UUID id){
        Customer customer = customerService.findByCustomerId(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        MessageFormat.format("customer with id: {0} does not exist!", id)));

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody CustomerDto newCustomerDto){
        Customer customer = Customer.builder()
                .firstName(newCustomerDto.getFirstName())
                .lastName(newCustomerDto.getLastName())
                .dateOfBirth(newCustomerDto.getDateOfBirth())
                .gender(newCustomerDto.getGender())
                .email(newCustomerDto.getEmail())
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .build();

        customerService.save(customer);
        return new ResponseEntity<>(
                MessageFormat.format("customer: {0} {1} successfully created!",customer.getLastName(), customer.getFirstName()),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id")UUID id, @Valid @RequestBody CustomerDto newCustomerDto){

        Customer customer = customerService.findByCustomerId(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        MessageFormat.format("customer with id: {0} does not exist!", id)));

        customer.setFirstName(newCustomerDto.getFirstName());
        customer.setLastName(newCustomerDto.getLastName());
        customer.setDateOfBirth(newCustomerDto.getDateOfBirth());
        customer.setGender(newCustomerDto.getGender());
        customer.setEmail(newCustomerDto.getEmail());
        customer.setLastUpdatedDate(LocalDateTime.now());

        customerService.save(customer);
        return new ResponseEntity<>(
                MessageFormat.format("customer with id: {0} successfully updated!",customer.getId(), customer.getFirstName()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id")UUID id){
        Customer customer = customerService.findByCustomerId(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        MessageFormat.format("customer with id: {0} does not exist!", id)));

        customerService.deleteCustomer(customer);

        return new ResponseEntity<>(
                MessageFormat.format("customer with id: {0} successfully deleted!",id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCountOfCustomers(){
        return new ResponseEntity<>(customerService.getCountOfCustomers(), HttpStatus.OK);
    }
}
