package com.min.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		List<HttpMessageConverter<?>> forRemove = new ArrayList<>();
		
		for(HttpMessageConverter<?> converter:converters) {
			if(converter instanceof MappingJackson2HttpMessageConverter) {
				forRemove.add(converter);
			}
		}
		
		converters.removeAll(forRemove);
		
		Hibernate5Module module = new Hibernate5Module();
		module.configure(Feature.FORCE_LAZY_LOADING, true);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(module);
		
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
	}
	
}
