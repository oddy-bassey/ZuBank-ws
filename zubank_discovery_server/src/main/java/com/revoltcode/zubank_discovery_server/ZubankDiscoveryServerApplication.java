package com.revoltcode.zubank_discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ZubankDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZubankDiscoveryServerApplication.class, args);
	}

}
