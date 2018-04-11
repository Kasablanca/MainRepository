package com.min.web;

import java.util.Date;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

@Configuration
@ServletComponentScan
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new Converter<String,Date>(){
			@Override
			public Date convert(String source) {
				if(StringUtils.isEmpty(source))
		    		return null;
		        return new Date(Long.valueOf(source));
			}
		});
	}
	
	@Bean
	@Primary
	public ObjectMapper getObjectMapper() {
		Hibernate5Module module = new Hibernate5Module();
		module.configure(Feature.FORCE_LAZY_LOADING, true);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(module);
		
		return objectMapper;
	}
	
}
