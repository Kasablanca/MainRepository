package com.min.someapp.web;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.someapp.web.security.AuthorityInterceptor;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;

@Configuration
//@ServletComponentScan
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthorityInterceptor authorityInteceptor;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorityInteceptor).addPathPatterns("/**");
	}
	
	@Bean
	public FilterRegistrationBean<MDCInsertingServletFilter> registerServlet() {
		FilterRegistrationBean<MDCInsertingServletFilter> bean = new FilterRegistrationBean<>(new MDCInsertingServletFilter());
		bean.addUrlPatterns("/*");
		return bean;
	}
	
	@PostConstruct
	public void init() {
		objectMapper.setTimeZone(TimeZone.getDefault());
	}
/*	
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
	}*/
	
}
