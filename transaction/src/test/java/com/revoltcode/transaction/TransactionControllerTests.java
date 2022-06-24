package com.revoltcode.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revoltcode.account.common.dto.TransactionType;
import com.revoltcode.transaction.model.Transaction;
import com.revoltcode.transaction.service.TransactionService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application.yml")
@AutoConfigureMockMvc
@SpringBootTest
class TransactionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TransactionService transactionService;

	// Inserting data into the database before each test
	@BeforeEach
	public void populateDatabase(){
		jdbcTemplate.execute("insert into transaction (ACCOUNT_ID, TRANSACTION_TYPE, AMOUNT, DESCRIPTION, TRANSACTION_TIME, CREATED_DATE, ID) " +
				"values ('ef3b95f1-01b1-4dcf-9a65-0cc798e159c1', 'DEPOSIT', 30000, 'cash deposit', '2022-06-11 20:09:14', '2021-08-11 20:09:14', 'ef3b95f1-01b1-4dcf-9a65-0bb798e159c1')");
	}

	// Removing all the data from the database after each test
	@AfterEach
	public void cleanDatabase(){
		jdbcTemplate.execute("delete from transaction");
	}

	@DisplayName("Save transaction test.")
	@Test
	public void saveTransaction(){
		transactionService.save(Transaction.builder()
				.accountId("03975755-ba34-4b68-99db-d80515b83279")
				.transactionType(TransactionType.DEPOSIT)
				.amount(BigDecimal.valueOf(7000))
				.description("An amount of 32,802 was deposited into () account with id: 03815755-ba34-4b68-99db-c80515b83279")
				.transactionTime(LocalDateTime.now())
				.createdDate(LocalDateTime.now())
				.build());

		List<Transaction> transaction = transactionService.findByAccountId("03975755-ba34-4b68-99db-d80515b83279");

		// check if a deposit transaction of amount 7000 exists
		assertTrue(transaction.stream().filter(data ->
						data.getAmount().compareTo(BigDecimal.valueOf(7000))==0
								&& data.getTransactionType().equals(TransactionType.DEPOSIT))
				.collect(Collectors.toList()).size() == 1);
	}

	@DisplayName("Get all transactions.")
	@Test
	public void getAllTransaction() throws Exception {
		mockMvc.perform(get("/api/v1/transactions/"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@DisplayName("Get transaction by id controller test.")
	@Test
	public void getTransactionById() throws Exception {
		UUID id = transactionService.findByAccountId("ef3b95f1-01b1-4dcf-9a65-0cc798e159c1").get(0).getId();
		Optional<Transaction> transaction = transactionService.findById(id);
		assertTrue(transaction.isPresent());

		mockMvc.perform(get("/api/v1/transactions/byId/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(transaction)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", Matchers.is(transaction.get().getId().toString())));
	}

	@DisplayName("Get transaction by account id controller test.")
	@Test
	public void getTransactionByAccountId() throws Exception {
		mockMvc.perform(get("/api/v1/transactions/byAccountId/{accountId}", "ef3b95f1-01b1-4dcf-9a65-0cc798e159c1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@DisplayName("Get count of stored transactions controller test.")
	@Test
	public void getTransactionCount() throws Exception {
		mockMvc.perform(get("/api/v1/transactions/count"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string("1"));
	}
}
