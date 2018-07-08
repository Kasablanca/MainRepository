package com.syhd.ahriman.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.syhd.ahriman.web.security.AuthorityInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthorityInterceptor loginLogoutInteceptor;
	
	@Bean
	public MultipartResolver getMultipartResolver() {
		MultipartResolver resolver = new MultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxInMemorySize(1024000); // 1kb
		resolver.setMaxUploadSize(-1);
		resolver.setMaxUploadSizePerFile(-1);
		
		return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginLogoutInteceptor).addPathPatterns("/**");
	}
	
}
