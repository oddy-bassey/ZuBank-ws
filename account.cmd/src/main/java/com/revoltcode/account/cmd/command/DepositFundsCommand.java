package com.revoltcode.account.cmd.command;

import com.revoltcode.cqrs.core.command.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositFundsCommand extends BaseCommand {
    private BigDecimal amount;
}
