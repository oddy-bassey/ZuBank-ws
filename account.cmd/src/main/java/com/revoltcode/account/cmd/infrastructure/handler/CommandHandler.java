package com.revoltcode.account.cmd.infrastructure.handler;

import com.revoltcode.account.cmd.command.*;

public interface CommandHandler {

    void handle(OpenAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithdrawFundsCommand command);
    void handle(TransferFundsCommand command);
    void handle(CloseAccountCommand command);
    void handle(RestoreReadDbCommand command);
}
