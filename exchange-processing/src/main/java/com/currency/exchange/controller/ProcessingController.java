package com.currency.exchange.controller;

import com.currency.exchange.dto.ExchangeMoneyDto;
import com.currency.exchange.dto.NewAccountDto;
import com.currency.exchange.dto.PutAccountMoneyDto;
import com.currency.exchange.model.Account;
import com.currency.exchange.service.AccountService;
import com.currency.exchange.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("processing")
public class ProcessingController {
	private final AccountService accountService;
	private final ExchangeService exchangeService;

	@PostMapping("/account")
	public Account createAccount(@RequestBody NewAccountDto accountDto) {
		return accountService.createAccount(accountDto);
	}

	@PutMapping("/account/{id}")
	public Account putMoney(@PathVariable("id") Long accountId, @RequestBody PutAccountMoneyDto accountMoneyDto) {
		return accountService.addMoneyToAccount(accountMoneyDto.getUid(), accountId, accountMoneyDto.getMoney());
	}

	@PutMapping("/exchange/{uid}")
	public BigDecimal exchange(@PathVariable("uid") String uid, @RequestBody ExchangeMoneyDto moneyDto) {
		return exchangeService.exchangeCurrency(uid, moneyDto.getFromAccountId(), moneyDto.getToAccountId(), moneyDto.getMoney());
	}

	@GetMapping("/accounts/{userId}")
	public List<Account> getAllAccount(@PathVariable("userId") Long userId) {
		return accountService.getAllAccount(userId);
	}
}
