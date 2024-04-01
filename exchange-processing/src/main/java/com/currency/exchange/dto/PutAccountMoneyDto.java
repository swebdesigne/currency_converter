package com.currency.exchange.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PutAccountMoneyDto {
	@JsonAlias("uid")
	private String uid;
	@JsonAlias("account")
	private long accountId;
	@JsonAlias("amount")
	private BigDecimal money;
}
