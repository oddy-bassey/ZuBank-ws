package com.revoltcode.crm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class CrmDbIntegrationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void populateDatabase(){
        jdbcTemplate.execute("insert into customer (CREATED_DATE, LAST_UPDATED_DATE, DATE_OF_BIRTH, EMAIL, FIRST_NAME, GENDER, LAST_NAME, ID) " +
                "values ('2022-06-11 20:09:14', '2021-08-11 20:09:14', '2021-09-12', 'dami@gmail.com', 'dami', 'female', 'adams', 'ef3b95f1-01b1-4dcf-9a65-0bb798e159c1')");
    }

    @AfterEach
    public void cleanDatabase(){
        jdbcTemplate.execute("delete from customer");
    }

    @Test
    public void dbContainsData(){
        int numberOfCustomersSaved = jdbcTemplate.queryForObject("select count(*) from customer", Integer.class);
        assertTrue(numberOfCustomersSaved>0);
    }
}
