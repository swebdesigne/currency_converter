package com.currency.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CurrencyConverterApplication {
	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class);
	}
}
