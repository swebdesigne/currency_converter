package com.currency.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(CurrencyClientConfig.class)
public class CurrencyConverterApplication {
	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class);
	}
}
