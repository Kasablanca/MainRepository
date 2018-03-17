package com.lee.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.lee.filter.TimeFilter;
import com.lee.interceptor.TimeInterceptor;
import com.lee.listener.ListenerTest;
import com.lee.servlet.ServletTest;
import com.lee.socket.WebSocketServer;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Autowired
	private TimeInterceptor timeInterceptor;

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		return converter;
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new ServletTest(),"/servletTest");
	}

	@Bean
	public FilterRegistrationBean timeFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		TimeFilter timeFilter = new TimeFilter();
		registrationBean.setFilter(timeFilter);

		List<String> urls = new ArrayList<>();
		urls.add("/*");
		registrationBean.setUrlPatterns(urls);

		return registrationBean;
	}

	@Bean
	public ServletListenerRegistrationBean<ListenerTest> servletListenerRegistrationBean() {
		return new ServletListenerRegistrationBean<ListenerTest>(new ListenerTest());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeInterceptor);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/fastjson/**").allowedOrigins("http://localhost:8081");// 允许 8088 端口访问
	}

	@Bean
	public WebSocketHandler webSocketServer() {  
		return new WebSocketServer();  
	} 

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketServer(), "/webSocketServer/*"); 
	}

}
