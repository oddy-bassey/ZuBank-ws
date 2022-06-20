package com.revoltcode.account.query.repository;

import com.revoltcode.account.common.dto.AccountType;
import com.revoltcode.account.query.domain.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {

    Optional<BankAccount> findByCustomerId(String customerId);
    Optional<BankAccount> findByCustomerIdAndAccountType(String customerId, AccountType accountType);
}
