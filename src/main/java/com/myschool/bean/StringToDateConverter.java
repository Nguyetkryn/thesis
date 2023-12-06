package com.myschool.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateConverter implements Converter<String, Date> {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	// Chuyển đổi từ chuỗi sang ngày
	@Override
	public Date convert(String value) {

		if (value.isBlank()) {
			return null;
		}

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			return dateFormat.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

}
