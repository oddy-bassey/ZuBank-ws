package com.revoltcode.account.query;

import com.revoltcode.account.query.infrastructure.handler.QueryHandler;
import com.revoltcode.account.query.query.*;
import com.revoltcode.cqrs.core.infrastructure.dispatcher.QueryDispatcher;
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
public class QueryApplication {

	@Autowired
	private final QueryDispatcher queryDispatcher;

	@Autowired
	private final QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		queryDispatcher.registerHandler(FindAllAccountQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByCustomerIdQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByCustomerIdAndAccountTypeQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(GetAccountCountQuery.class, queryHandler::handle);
	}
}
