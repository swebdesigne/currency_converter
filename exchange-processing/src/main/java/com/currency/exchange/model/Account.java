package com.currency.exchange.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Account {
	@Id
	@SequenceGenerator(name = "account_generator", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "account_generator", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "USER_ID", nullable = false)
	private Long userId;
	@Column(name = "CURRENCY_CODE", length = 3, nullable = false)
	private String currencyCode;
	@Column(name = "BALANCE", length = 3, nullable = false)
	private BigDecimal balance;
}
