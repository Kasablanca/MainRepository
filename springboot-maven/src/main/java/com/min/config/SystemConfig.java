package com.min.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

	@Autowired
	private Environment env;
	
	@PostConstruct
	private void init() {
		System.out.println(Arrays.toString(env.getActiveProfiles()));
		System.out.println(env.getProperty("server.port"));
	}
	
}
