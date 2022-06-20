package com.revoltcode.cqrs.core.infrastructure.handler;

import com.revoltcode.cqrs.core.command.BaseCommand;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {

    void handle(T command);
}
