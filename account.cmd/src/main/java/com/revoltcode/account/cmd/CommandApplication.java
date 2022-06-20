package com.revoltcode.account.cmd;

import com.revoltcode.account.cmd.command.*;
import com.revoltcode.account.cmd.infrastructure.handler.CommandHandler;
import com.revoltcode.cqrs.core.infrastructure.dispatcher.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class CommandApplication {

	@Autowired
	private final CommandDispatcher commandDispatcher;

	@Autowired
	private final CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandler(){
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(TransferFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(RestoreReadDbCommand.class, commandHandler::handle);
	}

}
