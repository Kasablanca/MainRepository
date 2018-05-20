package com.syhd.ahriman.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("authorization")
public class AuthorizationController {

	@RequestMapping("index")
	public String index() {
		return "authorization/index";
	}
	
}
