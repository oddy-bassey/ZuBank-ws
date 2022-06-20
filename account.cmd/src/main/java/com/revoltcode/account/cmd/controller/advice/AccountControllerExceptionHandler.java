package com.revoltcode.account.cmd.controller.advice;

import com.revoltcode.account.common.dto.ErrorMessage;
import com.revoltcode.account.common.exception.AccountNotFoundException;
import com.revoltcode.account.common.exception.FraudulentTransactionException;
import com.revoltcode.account.common.exception.InsufficientFundsException;
import com.revoltcode.account.common.exception.NegativeDepositAmountException;
import com.revoltcode.cqrs.core.exception.AggregateNotFoundException;
import com.revoltcode.cqrs.core.exception.ConcurrencyException;
import com.revoltcode.cqrs.core.exception.EventStreamNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class AccountControllerExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage accountNotFoundException(AccountNotFoundException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(FraudulentTransactionException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage fraudulentTransactionException(FraudulentTransactionException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(NegativeDepositAmountException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage negativeDepositAmountException(NegativeDepositAmountException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage insufficientFundsException(InsufficientFundsException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(AggregateNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage aggregateNotFoundException(AggregateNotFoundException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(ConcurrencyException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage concurrencyException(ConcurrencyException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(EventStreamNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage eventStreamNotFoundException(EventStreamNotFoundException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage accessDeniedException(AccessDeniedException ex, WebRequest request) {

        return ErrorMessage.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage generalException(Exception ex, WebRequest request) {

        ex.printStackTrace();
        return ErrorMessage.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }
}
