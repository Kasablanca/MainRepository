package com.min.web;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

@Configuration
@ServletComponentScan
public class WebConfig implements WebMvcConfigurer {
	
	@Bean
	@Primary
	public ObjectMapper getObjectMapper() {
		Hibernate5Module module = new Hibernate5Module();
		module.configure(Feature.FORCE_LAZY_LOADING, true);
		module.configure(Feature.FORCE_LAZY_LOADING, true);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(module);
		//objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		return objectMapper;
	}
	
	@Bean
	public MultipartResolver getMultipartResolver() {
		MultipartResolver resolver = new MultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxInMemorySize(1024000);
		resolver.setMaxUploadSize(-1);
		resolver.setMaxUploadSizePerFile(-1);
		
		return resolver;
	}
	
}
