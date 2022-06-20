package com.revoltcode.account.cmd.controller;

import com.revoltcode.account.cmd.command.*;
import com.revoltcode.account.cmd.dto.OpenAccountResponse;
import com.revoltcode.account.common.dto.BaseResponse;
import com.revoltcode.cqrs.core.exception.AggregateNotFoundException;
import com.revoltcode.cqrs.core.infrastructure.dispatcher.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Logger;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private final CommandDispatcher commandDispatcher;

    @PostMapping("/openAccount")
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);

        final BigDecimal initialCredit = command.getInitialCredit();
        commandDispatcher.send(command);

        String extraMsg = "";
        if(initialCredit.compareTo(BigDecimal.ZERO) > 0) {
            extraMsg = " A deposit of "+initialCredit+" amount successfully posted to account.";
        }
        return new ResponseEntity<>(new OpenAccountResponse("Bank account created successfully!"
                +extraMsg, id), HttpStatus.OK);
    }

    @PutMapping("/depositFunds/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
                                                   @RequestBody DepositFundsCommand command){
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Funds deposit of {0} amount completed successfully on account with id {1}!",
                command.getAmount(), id)), HttpStatus.OK);
    }

    @PutMapping("/withdrawFunds/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String id,
                                                     @RequestBody WithdrawFundsCommand command){
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Funds withdrawal of {0} amount completed successfully on account with id {1}!",
                command.getAmount(), id)), HttpStatus.OK);
    }

    @PutMapping("/transferFunds/{debitAccountId}/{creditAccountId}")
    public ResponseEntity<BaseResponse> transferFunds(@PathVariable(value = "debitAccountId") String debitAccountId,
                                                      @PathVariable(value = "creditAccountId") String creditAccountId,
                                                      @RequestBody TransferFundsCommand command){
        command.setId(debitAccountId);
        command.setCreditAccountId(creditAccountId);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Funds transfer of {0} amount from account with id {1} to account with id: {2} completed successfully!",
                command.getAmount(), debitAccountId, creditAccountId)), HttpStatus.OK);
    }

    @DeleteMapping("/closeAccount/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id){

        commandDispatcher.send(new CloseAccountCommand(id));
        return new ResponseEntity<>(new BaseResponse(MessageFormat.format("Bank account with id: {0} closed successfully!", id)), HttpStatus.CREATED);
    }
}






















