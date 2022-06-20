package com.revoltcode.transaction.service;

import com.revoltcode.transaction.model.Transaction;
import com.revoltcode.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findByAccountId(String associatedAccountId) {
        return transactionRepository.findByAccountId(associatedAccountId);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public long getTransactionCount() {
        return transactionRepository.count();
    }
}
