package com.currency.converter.client;

import java.time.LocalDate;

public interface HttpCurrencyDateRateClient {
	String requestByDate(LocalDate date);
}
