package com.currency.converter.controller;

import com.currency.converter.service.CbService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("currency")
public class CurrencyController {
	private final CbService currencyService;

	@GetMapping("/rate/{code}")
	public BigDecimal currencyRate(@PathVariable("code") String code) {
		return currencyService.requestByCurrencyCode(code);
	}
}
