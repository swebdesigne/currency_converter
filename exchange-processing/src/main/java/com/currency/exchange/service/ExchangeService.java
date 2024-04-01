package com.currency.exchange.service;

import com.currency.exchange.model.Account;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ExchangeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeService.class);

	public static final String CURRENCY_RUB = "RUB";

	private final AccountService accountService;
	private final CurrencyService currencyService;

	@Lazy
	@Autowired
	private ExchangeService exchangeService;

	public BigDecimal exchangeCurrency(String uuid, Long fromAccount, Long toAccount, BigDecimal amount) {
		Account source = accountService.getAccountById(fromAccount);
		Account target = accountService.getAccountById(toAccount);

		LOGGER.info("Exchange operation {} from account {} to account {} started", uuid, fromAccount, toAccount);

		BigDecimal result;
		if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && CURRENCY_RUB.equals(target.getCurrencyCode())) {
			BigDecimal rate = currencyService.loadCurrencyRate(source.getCurrencyCode());
			result = exchangeService.exchangeWithMultiply(uuid, source, target, rate, amount);
		} else if (CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
			BigDecimal rate = currencyService.loadCurrencyRate(target.getCurrencyCode());
			BigDecimal multiplier = new BigDecimal(1).divide(rate, 4, RoundingMode.HALF_DOWN);
			result = exchangeService.exchangeWithMultiply(uuid, source, target, multiplier, amount);
		} else if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
			BigDecimal rateFrom = currencyService.loadCurrencyRate(source.getCurrencyCode());
			BigDecimal rateTo = currencyService.loadCurrencyRate(target.getCurrencyCode());
			result = exchangeThroughRub(uuid, source, target, rateFrom, rateTo, amount);
		} else if (CURRENCY_RUB.equals(source.getCurrencyCode()) && CURRENCY_RUB.equals(target.getCurrencyCode())) {
			result = simpleExchange(uuid, source, target, amount);
		} else {
			throw new IllegalStateException("Unknown behavior");
		}

		LOGGER.info("Exchange operation {} from account {} to account {} completed", uuid, fromAccount, toAccount);
		return result;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public BigDecimal simpleExchange(String uuid, Account source, Account target, BigDecimal amount) {
		accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
		accountService.addMoneyToAccount(uuid, target.getId(), amount);
		return amount;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public BigDecimal exchangeWithMultiply(String uuid, Account source, Account target, BigDecimal rate, BigDecimal amount) {
		accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
		BigDecimal result = amount.multiply(rate);
		accountService.addMoneyToAccount(uuid, target.getId(), amount);
		return result;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public BigDecimal exchangeThroughRub(String uuid, Account source, Account target,
	                                     BigDecimal rateFrom, BigDecimal rateTo, BigDecimal amount
	) {
		accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
		BigDecimal rub = amount.multiply(rateFrom);
		BigDecimal result = rub.divide(rateTo, 4, RoundingMode.HALF_DOWN);
		accountService.addMoneyToAccount(uuid, target.getId(), amount);
		return result;
	}
}
