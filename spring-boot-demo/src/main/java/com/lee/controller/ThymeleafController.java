package com.lee.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {

	@RequestMapping("hello")
	public String hello(Map<String,Object> map) {
		map.put("msg", "Hello Thymeleaf");
		return "hello";
	}
	
	@RequestMapping("chat")
	public String chat() {
		return "chat";
	}
	
	public static void main(String[] args) throws IOException {
		InputStream is = ThymeleafController.class.getResourceAsStream("/book.properties");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String input;
		while((input = br.readLine()) != null) {
			System.out.println(input);
		}
	}

}
