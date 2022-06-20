package com.revoltcode.account.cmd.command;

import com.revoltcode.account.common.dto.AccountType;
import com.revoltcode.cqrs.core.command.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String customerId;
    private AccountType accountType;
    private BigDecimal initialCredit;
}
