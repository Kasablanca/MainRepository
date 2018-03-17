package com.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication /*extends SpringBootServletInitializer*/ {
/*	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
