package com.revoltcode.crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revoltcode.crm.enumCategory.Gender;
import com.revoltcode.crm.model.Customer;
import com.revoltcode.crm.service.CustomerService;
import static org.hamcrest.collection.IsCollectionWithSize.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import static org.springframework.test.util.AssertionErrors.*;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Optional;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest

public class CrmRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    // Inserting data into the database before each test
    @BeforeEach
    public void populateDatabase(){
        jdbcTemplate.execute("insert into customer (CREATED_DATE, LAST_UPDATED_DATE, DATE_OF_BIRTH, EMAIL, FIRST_NAME, GENDER, LAST_NAME, ID) " +
                "values ('2022-06-11 20:09:14', '2021-08-11 20:09:14', '2021-09-12', 'dami@gmail.com', 'dami', 'female', 'adams', 'ef3b95f1-01b1-4dcf-9a65-0bb798e159c1')");
    }

    // Removing all the data from the database after each test
    @AfterEach
    public void cleanDatabase(){
        jdbcTemplate.execute("delete from customer");
    }

    @DisplayName("Get all customers test")
    @Test
    public void getAllCustomers() throws Exception {
        mockMvc.perform(get("/api/v1/customers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("Get customer by id test")
    @Test
    public void getCustomerById() throws Exception {
        Optional<Customer> customer = customerService.findByEmail("dami@gmail.com");
        assertTrue("checking if customer with email (dami@gmail.com) exists, then update lastname!", customer.isPresent());

        mockMvc.perform(get("/api/v1/customers/{id}", customer.get().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(customer.get().getId().toString())))
                .andExpect(jsonPath("$.firstName", Matchers.is(customer.get().getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(customer.get().getLastName())));
    }

    @DisplayName("Save customer test")
    @Test
    public void createCustomer() throws Exception {
        Customer newCustomer = Customer.builder()
                .firstName("Danny")
                .lastName("Richard")
                .gender(Gender.male)
                .dateOfBirth(LocalDate.of(2012, 4,5))
                .email("dannyrichard@google.com")
                .build();

        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(newCustomer)))
                .andExpect(status().isCreated());

        Optional<Customer> customer = customerService.findByEmail("dannyrichard@google.com");
        assertNotNull("if customer was created successfully, " +
                "then customer fetched should not be null!", customer.get());

    }

    @DisplayName("Save customer throws exception on missing fields test")
    @Test
    public void createCustomerIncompleteDetail() throws Exception {
        Customer newCustomer = Customer.builder()
                .firstName("Danny")
                .lastName("Richard")
                .gender(Gender.male)
                .build();

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(newCustomer)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Update customer test")
    @Test
    public void updateCustomer() throws Exception {
        Optional<Customer> customer = customerService.findByEmail("dami@gmail.com");
        assertTrue("checking if customer with email (dami@gmail.com) exists, then update lastname!", customer.isPresent());
        customer.get().setLastName("Buhari");

        mockMvc.perform(put("/api/v1/customers/{id}", customer.get().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")));

        assertEquals("Compare updated customer lastname with stored data to verify the customer was successfully updated!",
                customer.get().getLastName(),
                customerService.findByCustomerId(customer.get().getId()).get()
                        .getLastName());
    }

    @DisplayName("Delete customer test")
    @Test
    public void deleteCustomer() throws Exception {
        Optional<Customer> customer = customerService.findByEmail("dami@gmail.com");
        assertTrue("checking if customer with email (dami@gmail.com) exists!", customer.isPresent());

        mockMvc.perform(delete("/api/v1/customers/{id}", customer.get().getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")));

        assertFalse("Check to confirm customer is deleted successfully!",
                customerService.findByCustomerId(customer.get().getId()).isPresent());

    }

    @DisplayName("Get count of customer test")
    @Test
    public void getCustomerCount() throws Exception {
        mockMvc.perform(get("/api/v1/customers/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1"));
    }
}














