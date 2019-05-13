package com.payconiq.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

public class EcommerceUtils {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getCurrentDateTimeStr() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
		LocalDateTime now = LocalDateTime.now();
		String timestamp = now.format(formatter);
		return timestamp;
	}

	public static long generateRandomNumber() {
		long x = 1234567L;
		long y = 23456789L;
		Random r = new Random();
		long number = x + ((long) (r.nextDouble() * (y - x)));
		return number;
	}

	public static HttpHeaders addResponseHeaders(String uuid) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (StringUtils.isNoneBlank(uuid)) {
			httpHeaders.add(EcommerceConstants.UUID_HEADER, uuid);
		}
		return httpHeaders;
	}

}