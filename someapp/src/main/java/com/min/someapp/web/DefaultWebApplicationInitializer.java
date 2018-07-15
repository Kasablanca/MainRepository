package com.min.someapp.web;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.web.WebApplicationInitializer;

//@Component
public class DefaultWebApplicationInitializer implements WebApplicationInitializer, 
		ServletContainerInitializer, ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("WebApplicationInitializer or ServletContextInitializer onStartup");
	}

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		System.out.println("ServletContainerInitializer onStartup");
	}

}
