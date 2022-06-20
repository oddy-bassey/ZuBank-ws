package com.revoltcode.account.common.event.account;
import com.revoltcode.cqrs.core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsWithdrawnEvent extends BaseEvent {
    private BigDecimal amount;
}
