package com.syhd.ahriman.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Bean
	public MultipartResolver getMultipartResolver() {
		MultipartResolver resolver = new MultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxInMemorySize(1024000); // 1kb
		resolver.setMaxUploadSize(-1);
		resolver.setMaxUploadSizePerFile(-1);
		
		return resolver;
	}
	
}
