package com.currency.exchange.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeMoneyDto {
	@JsonAlias("uid")
	private String ExchangeUid;
	@JsonAlias("from")
	private Long fromAccountId;
	@JsonAlias("to")
	private Long toAccountId;
	@JsonAlias("money")
	private BigDecimal money;
}
