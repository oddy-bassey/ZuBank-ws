package com.revoltcode.zubank_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ZubankConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZubankConfigServerApplication.class, args);
	}

}
