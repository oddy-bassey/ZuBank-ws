package com.revoltcode.account.common.event.transaction;
import com.revoltcode.account.common.dto.TransactionType;
import com.revoltcode.cqrs.core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransactionEvent extends BaseEvent {
    private String accountName;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private LocalDateTime transactionTime;
}
