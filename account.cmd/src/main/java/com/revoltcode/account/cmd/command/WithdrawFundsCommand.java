package com.revoltcode.account.cmd.command;

import com.revoltcode.cqrs.core.command.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private BigDecimal amount;
}
