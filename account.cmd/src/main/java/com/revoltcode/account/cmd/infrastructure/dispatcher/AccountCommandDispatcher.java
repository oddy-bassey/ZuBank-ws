package com.revoltcode.account.cmd.infrastructure.dispatcher;

import com.revoltcode.cqrs.core.command.BaseCommand;
import com.revoltcode.cqrs.core.infrastructure.dispatcher.CommandDispatcher;
import com.revoltcode.cqrs.core.infrastructure.handler.CommandHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, e -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if(handlers == null || handlers.size() == 0) throw new RuntimeException("No command handler was registered!");
        if(handlers.size() > 1) throw new RuntimeException("We cannot send a command to more than one handler!");
        handlers.get(0).handle(command);
    }
}
