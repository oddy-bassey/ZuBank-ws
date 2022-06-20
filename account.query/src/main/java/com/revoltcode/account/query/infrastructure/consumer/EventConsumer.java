package com.revoltcode.account.query.infrastructure.consumer;

import com.revoltcode.account.common.event.account.AccountClosedEvent;
import com.revoltcode.account.common.event.account.AccountOpenedEvent;
import com.revoltcode.account.common.event.account.FundsDepositedEvent;
import com.revoltcode.account.common.event.account.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment acknowledgment);
    void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);
}
