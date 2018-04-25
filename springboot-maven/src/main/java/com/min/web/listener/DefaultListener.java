package com.min.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("default listener!")
public class DefaultListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("DefaultListener contextInitialized!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
