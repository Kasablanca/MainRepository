package com.min;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		SpringApplication application = new SpringApplication(Application.class);
		application.setLogStartupInfo(false);
		//application.setBannerMode(Mode.OFF);
		application.run(args);
	}

}
