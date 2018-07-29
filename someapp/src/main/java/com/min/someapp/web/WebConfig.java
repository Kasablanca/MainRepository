package com.min.someapp.web;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.someapp.web.security.AuthorityInterceptor;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;

@Configuration
@ServletComponentScan
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthorityInterceptor authorityInteceptor;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorityInteceptor).addPathPatterns("/**");
		
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setIgnoreInvalidLocale(true);
		localeChangeInterceptor.setParamName("siteLanguage");
		registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
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
		
		ObjectMapper objectMapper = new ObjectMapper();s
		objectMapper.registerModule(module);
		//objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		return objectMapper;
	}*/
	
	@Bean
	public MultipartResolver getMultipartResolver() {
		MultipartResolver resolver = new MultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxInMemorySize(1024000);
		resolver.setMaxUploadSize(-1);
		resolver.setMaxUploadSizePerFile(-1);
		
		return resolver;
	}
	
	@Bean("messageSource")
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:language/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean("localeResolver")
	public LocaleResolver getLocaleResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("siteLanguage");
		localeResolver.setCookieMaxAge(31536000);//one year
		return localeResolver;
	}
	
}
