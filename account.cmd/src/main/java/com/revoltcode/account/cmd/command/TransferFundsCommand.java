package com.revoltcode.account.cmd.command;

import com.revoltcode.cqrs.core.command.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferFundsCommand extends BaseCommand {
    private String creditAccountId;
    private BigDecimal amount;
}
