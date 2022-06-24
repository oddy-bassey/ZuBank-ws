package com.revoltcode.transaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("/application.yml")
@SpringBootTest
public class TransactionDbIntegrationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @DisplayName("Querying the database to get total count of data stored.")
    @Test
    public void dbContainsData(){
        int numberOfSavedTransactions = jdbcTemplate.queryForObject("select count(*) from transaction", Integer.class);
        assertTrue(numberOfSavedTransactions>0);
    }
}
