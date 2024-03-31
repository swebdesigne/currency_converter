package com.currency.converter.service;

import com.currency.converter.client.HttpCurrencyDateRateClient;
import com.google.common.cache.Cache;
import org.springframework.stereotype.Service;

import javax.annotation.concurrent.ThreadSafe;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static java.util.stream.Collectors.toMap;

@Service
@ThreadSafe
public class CbService {
	private final Cache<LocalDate, Map<String, BigDecimal>> cache;
	private HttpCurrencyDateRateClient client;

	public CbService(HttpCurrencyDateRateClient client) {
		this.cache = newBuilder().build();
		this.client = client;
	}

	public BigDecimal requestByCurrencyCode(String code) {
		try {
			return cache.get(LocalDate.now(), this::callAllByCurrentDate).get(code);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, BigDecimal> callAllByCurrentDate() {
		var xml = client.requestByDate(LocalDate.now());
		var response = unmarshal(xml);
		return response.getValute().stream().collect(toMap(generated.ValCurs.Valute::getCharCode, item -> parseWithLocale(item.getValue())));
	}

	private BigDecimal parseWithLocale(String currency) {
		try {
			var v = NumberFormat.getNumberInstance(Locale.getDefault()).parse(currency).doubleValue();
			return BigDecimal.valueOf(v);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private generated.ValCurs unmarshal(String xml) {
		try (StringReader reader = new StringReader(xml)) {
			JAXBContext context = JAXBContext.newInstance(generated.ValCurs.class);
			return (generated.ValCurs) context.createUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
