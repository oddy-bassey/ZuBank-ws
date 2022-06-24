package com.revoltcode.crm;

import com.revoltcode.crm.enumCategory.Gender;
import com.revoltcode.crm.model.Customer;
import com.revoltcode.crm.repository.CustomerRepository;
import com.revoltcode.crm.service.CustomerService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class CrmServiceTests {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@BeforeEach
	public void loadData(){
		customerRepository.save(Customer.builder()
				.firstName("Richard")
				.lastName("David")
				.email("richarddavid@gmail.com")
				.dateOfBirth(LocalDate.of(2000,3,22))
				.gender(Gender.male)
				.createdDate(LocalDateTime.now())
				.lastUpdatedDate(LocalDateTime.now())
				.build());
	}

	@AfterEach
	public void deleteData(){
		customerRepository.deleteAll();
	}

	@DisplayName("Test that service layer can successfully store customer.")
	@Test
	public void saveCustomer(){
		Customer customer = customerService.save(Customer.builder()
				.firstName("Nina")
				.lastName("Alfred")
				.email("ninadaniel@gmail.com")
				.dateOfBirth(LocalDate.of(2003,3,22))
				.gender(Gender.female)
				.createdDate(LocalDateTime.now())
				.lastUpdatedDate(LocalDateTime.now())
				.build());

		assertNotNull(customer.getId());
	}

	@DisplayName("Test that service layer will fail trying to store incomplete customer data.")
	@Test
	public void saveCustomerWithMissingRequiredValues(){
		Customer customer = Customer.builder()
				.firstName("Nina")
				.dateOfBirth(LocalDate.of(2003,3,22))
				.gender(Gender.female)
				.createdDate(LocalDateTime.now())
				.build();

		assertThrows(TransactionSystemException.class, () -> customerService.save(customer));
	}

	@DisplayName("Test that service layer to successfully find customer by email.")
	@Test
	public void findCustomerByEmail(){
		Customer customer = customerService.findByEmail("richarddavid@gmail.com").get();
		assertEquals("richarddavid@gmail.com", customer.getEmail());
	}

	@DisplayName("Test that service layer can get all stored customers.")
	@Test
	public void findAllCustomer(){
		assertEquals(1,customerService.findAll().size());
	}

	@DisplayName("Test that service layer can successfully update a customer.")
	@Test
	public void updateCustomer(){
		Customer customer = customerService.findByEmail("richarddavid@gmail.com").get();
		customer.setLastName("Moose");
		Customer updatedCustomer = customerService.save(customer);
		assertEquals( "Moose", updatedCustomer.getLastName());
	}

	@DisplayName("Test that service layer can successfully delete a customer.")
	@Test
	public void deleteCustomer(){
		Customer customer = customerService.save(Customer.builder()
				.firstName("Nina")
				.lastName("Alfred")
				.email("ninadaniel@gmail.com")
				.dateOfBirth(LocalDate.of(2003,3,22))
				.gender(Gender.female)
				.createdDate(LocalDateTime.now())
				.lastUpdatedDate(LocalDateTime.now())
				.build());

		customerService.deleteCustomer(customer);
		assertTrue(customerService.findByEmail("ninadaniel@gmail.com").isEmpty());
	}

	@DisplayName("Test that service layer can return the correct count of stored customers.")
	@Test
	public void getCustomerCount(){
		assertEquals(1, customerService.getCountOfCustomers());
	}
}
