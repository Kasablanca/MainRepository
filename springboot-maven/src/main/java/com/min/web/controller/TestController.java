package com.min.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.dao.entity.User;

@Controller
@RequestMapping("test")
public class TestController {

	@RequestMapping("test1")
	public String test1(@RequestAttribute User user) {
		return "user/test1";
	}
	
	@ResponseBody
	@RequestMapping("text")
	public String text() {
		return "中国";
	}
	
}
