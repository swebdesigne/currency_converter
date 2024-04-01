package com.currency.exchange.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CurrencyService {
	private final RestTemplate restClient;
	@Value(value = "${service.currency.url}")
	private String currencyUrl;

	public BigDecimal loadCurrencyRate(String code) {
		return restClient.getForObject(
				currencyUrl + "/currency/rate/{code}",
				BigDecimal.class,
				code
		);
	}
}
