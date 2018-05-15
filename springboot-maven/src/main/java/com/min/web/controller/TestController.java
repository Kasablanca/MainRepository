package com.min.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.min.dao.entity.User;

@Controller
@RequestMapping("test")
public class TestController {

	@RequestMapping("test1")
	public String test1(@RequestAttribute User user) {
		return "user/test1";
	}
	
	
	
}
