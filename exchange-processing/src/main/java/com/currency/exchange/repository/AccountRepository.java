package com.currency.exchange.repository;

import com.currency.exchange.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
	List<Account> findByUserId(Long id);
}
