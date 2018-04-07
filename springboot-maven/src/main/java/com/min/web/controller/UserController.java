package com.min.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.dao.entity.User;

@Controller
@RequestMapping("user")
public class UserController {

	@RequestMapping("/{id}")
	public ModelAndView detail(@PathVariable("id")User user) {
		ModelAndView mav = new ModelAndView("user/detail");
		mav.addObject("user", user);
		try {
			System.out.println(new ObjectMapper().writeValueAsString(user));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
}
