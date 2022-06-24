package com.revoltcode.transaction;

import com.revoltcode.account.common.dto.TransactionType;
import com.revoltcode.transaction.model.Transaction;
import com.revoltcode.transaction.repository.TransactionRepository;
import com.revoltcode.transaction.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@TestPropertySource("/application.yml")
@SpringBootTest
class TransactionServiceTests {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void loadData(){
        transactionRepository.save(Transaction.builder()
                        .accountId("03815755-ba34-4b68-99db-c80515b83279")
                        .transactionType(TransactionType.DEPOSIT)
                        .amount(BigDecimal.valueOf(500))
                        .description("An amount of 32,802 was deposited into () account with id: 03815755-ba34-4b68-99db-c80515b83279")
                        .transactionTime(LocalDateTime.now())
                        .createdDate(LocalDateTime.now())
                .build());
    }

    @AfterEach
    public void deleteData(){
        transactionRepository.deleteAll();
    }

    @DisplayName("Save transaction test.")
    @Test
    public void saveTransaction(){
        transactionRepository.save(Transaction.builder()
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

    @DisplayName("Get all transaction.")
    @Test
    public void getAllTransaction(){
        assertTrue(transactionService.findAll().size()>0);
        assertEquals(1, transactionService.findAll().size());
    }

    @DisplayName("Get transaction by id test.")
    @Test
    public void getTransactionById(){
        UUID id = transactionRepository.findByAccountId("03815755-ba34-4b68-99db-c80515b83279").get(0).getId();
        Optional<Transaction> transaction = transactionService.findById(id);
        assertTrue(transaction.isPresent());
    }

    @DisplayName("Get transaction by account id test.")
    @Test
    public void getTransactionByAccountId(){
        List<Transaction> transaction = transactionService.findByAccountId("03815755-ba34-4b68-99db-c80515b83279");
        assertTrue(transaction.size()>0);
    }

    @DisplayName("Get count of stored transactions test.")
    @Test
    public void getTransactionCount(){
        assertEquals(1, transactionService.getTransactionCount());
    }
}















