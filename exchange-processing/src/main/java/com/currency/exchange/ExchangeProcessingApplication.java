package com.currency.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories("com.currency.exchange.repository")
public class ExchangeProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeProcessingApplication.class, args);
	}

}
