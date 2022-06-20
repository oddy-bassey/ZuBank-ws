package com.revoltcode.account.query.infrastructure.handler;

import com.revoltcode.account.common.event.account.*;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(FundsTransferedEvent event);
    void on(AccountClosedEvent event);
}
