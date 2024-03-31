package com.currency.converter.client;

import com.currency.converter.config.CurrencyClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CbrCurrencyClient implements HttpCurrencyDateRateClient {
	private final CurrencyClientConfig configClient;
	private static final String PATTERN = "dd/MM/yyyy";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

	@Override
	public String requestByDate(LocalDate date) {
		var baseUrl = configClient.getUrl();
		var url = buildUrlRequest(baseUrl, date);
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String buildUrlRequest(String baseUrl, LocalDate date) {
		return UriComponentsBuilder.fromHttpUrl(baseUrl)
				.queryParam("date_req", DATE_TIME_FORMATTER.format(date))
				.build().toUriString();
	}
}
