package com.min.someapp;

import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger("root");

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.warn("application started!");
		System.out.println("default timezone: "+TimeZone.getDefault().getDisplayName());
		System.out.println("default locale: "+Locale.getDefault().getDisplayName());
	}

}
