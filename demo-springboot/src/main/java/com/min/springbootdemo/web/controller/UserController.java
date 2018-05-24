package com.min.springbootdemo.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.springbootdemo.dao.model.User;
import com.min.springbootdemo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("add")
	public User add(User user) {
		return user;
	}
	
	@RequestMapping("date")
	public Date date(Date date) {
		return date;
	}
	
	@RequestMapping("delete/{id}")
	public int delete(@PathVariable Integer id) {
		return userService.delete(id);
	}
	
	@RequestMapping("find/{id}")
	public User find(@PathVariable Integer id) {
		return userService.findById(id);
	}
	
}
