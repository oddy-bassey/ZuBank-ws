package com.revoltcode.account.common.event.account;

import com.revoltcode.account.common.dto.AccountType;
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
public class AccountOpenedEvent extends BaseEvent {
    private String customerId;
    private String name;
    private AccountType accountType;
    private LocalDateTime createdDate;
    private BigDecimal openingBalance;
}
