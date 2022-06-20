package com.revoltcode.transaction.controller;

import com.revoltcode.account.common.dto.TransactionType;
import com.revoltcode.account.common.event.transaction.TransactionEvent;
import com.revoltcode.transaction.exception.TransactionNotFoundException;
import com.revoltcode.transaction.model.Transaction;
import com.revoltcode.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return new ResponseEntity<>(transactionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") UUID id){
        Transaction transaction = transactionService.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(MessageFormat.format("No transaction exists with the id: {0}", id)));

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/byAccountId/{accountId}")
    public ResponseEntity<?> getTransactionByAccountId(@PathVariable("accountId") String accountId){
        List<Transaction> transaction = transactionService.findByAccountId(accountId);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> saveTransaction(@RequestBody TransactionEvent event){

        Transaction transaction = Transaction.builder()
                .accountId(event.getId())
                .amount(event.getAmount())
                .description(event.getDescription())
                .transactionType(event.getTransactionType())
                .transactionTime(event.getTransactionTime())
                .createdDate(LocalDateTime.now())
                .build();

        transactionService.save(transaction);
        return new ResponseEntity<>("transaction persisted successfully!", HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCountOfTransactions(){
        return new ResponseEntity<>(transactionService.getTransactionCount(), HttpStatus.OK);
    }
}
