package com.syhd.ahriman.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.service.impl.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("index")
	public String index() {
		return "system/user/index";
	}
	
	@ResponseBody
	@RequestMapping("userDistribution")
	public List<Map<?, ?>> userDistribution(){
		return userService.userDistribution();
	}
	
	@ResponseBody
	@RequestMapping("add")
	public User add(User user) {
		return userService.insert2(user);
	}
	
	@ResponseBody
	@RequestMapping("{id}")
	public User findById(@PathVariable Integer id) {
		return userService.findById(id);
	}
	
}
