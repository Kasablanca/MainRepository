package com.min.demospring.web.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.demospring.dao.model.User;

@RestController
@RequestMapping("user")
public class UserController {

	@RequestMapping("add")
	public User add(User user) {
		return user;
	}
	
	@RequestMapping("date")
	public Date date(Date date) {
		return date;
	}
	
}
