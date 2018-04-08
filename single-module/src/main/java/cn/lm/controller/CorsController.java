package cn.lm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lm.model.User;

@Controller
@RequestMapping("/cors")
public class CorsController {

	@ResponseBody
	@RequestMapping("/getUser")
	public Object getUser() {
		return new User("MR.LEE",15);
	}
	
}
