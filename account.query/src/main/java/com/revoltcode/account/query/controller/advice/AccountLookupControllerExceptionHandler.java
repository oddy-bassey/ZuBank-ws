package com.revoltcode.account.query.controller.advice;

import com.revoltcode.account.common.dto.ErrorMessage;
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
public class AccountLookupControllerExceptionHandler {

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

        log.error(ex.toString());
        return ErrorMessage.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }
}
