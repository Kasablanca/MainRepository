package cn.lm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lm.model.User;

@Controller
@RequestMapping("/jsonp")
public class JsonpController {
	
	@ResponseBody
	@RequestMapping("/getUser")
	public Object getUser() {
		return new User("MR.LEE",15);
	}
	
	@CrossOrigin
	@RequestMapping("/getCORS")
	public Object getCORS() {
		return new User("MR.LEE",15);
	}
	
}
