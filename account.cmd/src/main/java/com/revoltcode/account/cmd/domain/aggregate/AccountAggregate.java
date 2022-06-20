package com.revoltcode.account.cmd.domain.aggregate;

import com.revoltcode.account.cmd.command.OpenAccountCommand;
import com.revoltcode.account.common.event.account.*;
import com.revoltcode.cqrs.core.domain.aggregate.AggregateRoot;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private BigDecimal balance;

    public Boolean getActive() {
        return active;
    }

    public BigDecimal getBalance(){
        return this.balance;
    }

    public AccountAggregate(OpenAccountCommand command, String accountName){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .customerId(command.getCustomerId())
                .name(accountName)
                .accountType(command.getAccountType())
                .openingBalance(command.getInitialCredit())
                .createdDate(LocalDateTime.now())
                .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(BigDecimal amount){
        if(!this.active) throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        if(amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalStateException("The deposit amount must be greater than 0.00!");

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance = getBalance().add(event.getAmount());
    }

    public void withdrawFunds(BigDecimal amount){
        if(!this.active) throw new IllegalStateException("Funds cannot be withdrawn from a closed account!");

        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance = getBalance().subtract(event.getAmount());
    }

    public void transferFunds(BigDecimal amount, String creditAccountId){
        if(!this.active) throw new IllegalStateException("Funds cannot be transferred from a closed account!");

        raiseEvent(FundsTransferedEvent.builder()
                .id(this.id)
                .amount(amount)
                .creditAccountId(creditAccountId)
                .build());
    }

    public void apply(FundsTransferedEvent event){
        this.id = event.getId();
        this.balance = getBalance().subtract(event.getAmount());
    }

    public void closeAccount(){
        if(!this.active) throw new IllegalStateException("The bank account has already been closed!");

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }
}
