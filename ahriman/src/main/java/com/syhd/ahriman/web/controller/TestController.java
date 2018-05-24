package com.syhd.ahriman.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.dto.Pagination;
import com.syhd.ahriman.dto.Sort;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.UserService;

@Controller
@RequestMapping("test")
public class TestController {
	
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping("text")
	public String text(){
		User user = new User();
		user.setId(1);
		user.setAge(28);
		user.setUsername("李敏（KING.LEE）");
		return "中国";
	}
	
	@ResponseBody
	@RequestMapping("object")
	public User object(){
		User user = new User();
		user.setId(1);
		user.setAge(28);
		user.setUsername("李敏（KING.LEE）");
		return user;
	}
	
	@RequestMapping("home")
	public ModelAndView home(){
		User user = new User();
		user.setId(1);
		user.setAge(28);
		user.setUsername("李敏（KING.LEE）");
		return new ModelAndView("test/home","user",user);
	}
	
	@RequestMapping("layout")
	public String layout(){
		return "layout";
	}
	
	@RequestMapping("dashboard")
	public String dashboard(){
		return "dashboard";
	}
	
	@ResponseBody
	@RequestMapping("user/{id}")
	public User user(@PathVariable Integer id) {
		return userService.findById(id);
	}
	
	@ResponseBody
	@RequestMapping("user")
	public TableData user(Pagination pagination,Sort sort){
		return userService.findAll(pagination,sort);
	}
	
	@ResponseBody
	@RequestMapping("user/add")
	public int add(User user) {
		return userService.insert(user);
	}
	
}
