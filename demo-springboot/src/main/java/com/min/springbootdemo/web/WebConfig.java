package com.min.springbootdemo.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	public static final String DEFAULT_DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	
	
}
