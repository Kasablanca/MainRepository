package com.syhd.ahriman.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.web.vo.Result;

@Controller
public class FacadeController {
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value="login",method=RequestMethod.POST)
	public Result login(User user) {
		return Result.getSuccessResult();
	}

	@RequestMapping("home")
	public String home() {
		return "home";
	}
	
	@RequestMapping("dashboard")
	public String dashboard() {
		return "dashboard";
	}
	
}
