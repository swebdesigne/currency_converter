package com.currency.exchange.service;

import com.currency.exchange.dto.NewAccountDto;
import com.currency.exchange.model.Account;
import com.currency.exchange.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	@Transactional
	public Account createAccount(NewAccountDto accountDto) {
		var account = new Account();
		account.setCurrencyCode(accountDto.getCurrentCode());
		account.setUserId(accountDto.getUserId());
		account.setBalance(new BigDecimal(0));
		return accountRepository.save(account);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public Account addMoneyToAccount(String uid, Long accountId, BigDecimal money) {
		var account = accountRepository.findById(accountId);
		return account.map(acc -> {
			var balance = acc.getBalance().add(money);
			acc.setBalance(balance);
			return accountRepository.save(acc);
		}).orElseThrow(() -> new IllegalArgumentException("Account with id + " + accountId + " not found"));
	}

	public Account getAccountById(Long id) {
		return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account with ID " + id + " not found"));
	}

	public List<Account> getAllAccount(Long userId) {
		return accountRepository.findByUserId(userId);
	}
}
