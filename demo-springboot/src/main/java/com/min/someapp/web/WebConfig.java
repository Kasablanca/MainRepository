package com.min.someapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.min.someapp.web.security.AuthorityInterceptor;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthorityInterceptor authorityInteceptor;
	
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
	
}
