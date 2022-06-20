package com.revoltcode.account.query.infrastructure.handler;

import com.revoltcode.account.common.dto.TransactionType;
import com.revoltcode.account.common.event.account.*;
import com.revoltcode.account.common.event.transaction.TransactionEvent;
import com.revoltcode.account.common.exception.AccountNotFoundException;
import com.revoltcode.account.query.infrastructure.service.TransactionService;
import com.revoltcode.account.query.repository.AccountRepository;
import com.revoltcode.account.query.domain.model.BankAccount;
import com.revoltcode.account.query.infrastructure.producer.TransactionEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountEventHandler implements EventHandler {

    private final AccountRepository accountRepository;
    private final TransactionEventProducer transactionEventProducer;
    private final TransactionService transactionFeignService;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .customerId(event.getCustomerId())
                .name(event.getName())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .createdDate(event.getCreatedDate())
                .lastUpdatedDate(event.getCreatedDate())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId())
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("Account with id: {0} does not exist!", event.getId())));

        var transactionTime = LocalDateTime.now();
        bankAccount.setBalance(bankAccount.getBalance().add(event.getAmount()));
        bankAccount.setLastUpdatedDate(transactionTime);

        accountRepository.save(bankAccount);

        var transaction = createTransactionEvent(
                bankAccount.getId(), bankAccount.getName(),
                event.getAmount(), TransactionType.DEPOSIT,
                MessageFormat.format("An amount of {0} was deposited into ({1}) account with id: {2}",
                        event.getAmount(), "", bankAccount.getId()),
                transactionTime);

        //ToDO: create a deposit event and make POST request to transactions
        transactionFeignService.postTransaction(transaction);
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId())
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("Account with id: {0} does not exist!", event.getId())));

        var transactionTime = LocalDateTime.now();
        bankAccount.setBalance(bankAccount.getBalance().subtract(event.getAmount()));
        bankAccount.setLastUpdatedDate(transactionTime);

        accountRepository.save(bankAccount);

        var transaction = createTransactionEvent(
                bankAccount.getId(), bankAccount.getName(),
                event.getAmount(), TransactionType.WITHDRAWAL,
                MessageFormat.format("An amount of {0} was withdrawn from ({1}) account with id: {2}",
                        event.getAmount(), "", bankAccount.getId()),
                transactionTime);

        //ToDO: create a withdraw event and make POST request to transactions
        transactionFeignService.postTransaction(transaction);
    }

    @Override
    public void on(FundsTransferedEvent event) {
        //ToDO: rewrite funds transfer
        var debitAccount = accountRepository.findById(event.getId())
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("The debit account with id: {0} does not exist!", event.getId())));

        var creditAccount = accountRepository.findById(event.getCreditAccountId())
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("The credit account with id: {0} does not exist!", event.getId())));

        var transactionTime = LocalDateTime.now();
        debitAccount.setBalance(debitAccount.getBalance().subtract(event.getAmount()));
        debitAccount.setLastUpdatedDate(transactionTime);
        accountRepository.save(debitAccount);

        creditAccount.setBalance(creditAccount.getBalance().add(event.getAmount()));
        creditAccount.setLastUpdatedDate(transactionTime);
        accountRepository.save(creditAccount);

        //ToDO: create a transfer event and make POST request to transactions
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }

    public TransactionEvent createTransactionEvent(String id, String accountName, BigDecimal amount,
                 TransactionType transactionType, String description, LocalDateTime transactionTIme){

        return TransactionEvent.builder()
                .id(id)
                .accountName(accountName)
                .amount(amount)
                .transactionType(transactionType)
                .description(description)
                .transactionTime(transactionTIme)
                .build();
    }
}
