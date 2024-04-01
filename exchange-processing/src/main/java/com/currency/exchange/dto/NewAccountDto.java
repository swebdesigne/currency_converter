package com.currency.exchange.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class NewAccountDto {
	@JsonAlias("currency")
	private String currentCode;
	@JsonAlias("user")
	private Long userId;
}
