package com.revoltcode.account.common.dto.rest;

import com.revoltcode.account.common.dto.TransactionType;
import com.revoltcode.cqrs.core.domain.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {

    private UUID id;
    private String accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionTime;
    private LocalDateTime createdDate;
}
