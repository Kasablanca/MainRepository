package com.min.demospring.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DefaultDateConverter implements Converter<String, Date> {
	
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Override
	public Date convert(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		sdf.setLenient(false);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			//throw new IllegalArgumentException("日期格式不正确，需要格式："+DEFAULT_DATE_PATTERN);
			return null;
		}
	}

}
