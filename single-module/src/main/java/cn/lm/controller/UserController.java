package cn.lm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lm.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/home")
	public String home() {
		return "user/home";
	}
	
	@RequestMapping("/test")
	public String test(User user) {
		return "redirect:/login.jsp";
	}
}
