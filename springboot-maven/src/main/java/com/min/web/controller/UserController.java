package com.min.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@RequestMapping("a")
	public String a(RedirectAttributes attr,String param1) {
		attr.addFlashAttribute("param1", param1);
		return "redirect:b";
	}
	
	@RequestMapping("b")
	public void b(Model model) {
		System.out.println(model.containsAttribute("param1"));
	}
	
}
