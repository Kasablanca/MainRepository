package com.min.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.dao.entity.User;
import com.min.dao.repository.UserRepository;

@Controller
@RequestMapping("user")
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

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
	
	@RequestMapping("test1")
	public String test1(String userid,@ModelAttribute(binding=false) User user) {
		try {
			System.out.println(new ObjectMapper().writeValueAsString(user));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		User user1 = userRepository.getOne(userid);
		user.setUserNick(user1.getUserNick());
		user.setUserId(user1.getUserId());
		user.setLinkMail(user1.getLinkMail());
		return "user/test1";
	}
	
	@RequestMapping("test2")
	public String test2(@SessionAttribute @ModelAttribute User user) {
		try {
			System.out.println(new ObjectMapper().writeValueAsString(user));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "user/test1";
	}
	
	@ResponseBody
	@RequestMapping("test3")
	public User test3(@SessionAttribute User user) {
		try {
			System.out.println(new ObjectMapper().writeValueAsString(user));
			user.setUserNick("updated");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@ResponseBody
	@RequestMapping("test4")
	public User test4(HttpSession session) {
		return (User) session.getAttribute("user");
	}
	
	@RequestMapping("userlogin")
	public ModelAndView userlogin(HttpSession session, String userNick,String password) {
		ModelAndView mav = new ModelAndView();
		
		User user = userRepository.findByUserNickAndLinkMail(userNick, password);
		if(user != null) {
			mav.setViewName("redirect:/user/test2");
			session.setAttribute("user", user);
			
		} else {
			mav.setViewName("redirect:/");
		}
		
		return mav;
	}
	
	@RequestMapping("test5")
	public String test5(@RequestAttribute(required=false) @ModelAttribute User user) {
		return "forward:test6";
	}
	
	@RequestMapping("test6")
	public ModelAndView test6(@RequestAttribute User user) {
		try {
			System.out.println(new ObjectMapper().writeValueAsString(user));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView("user/test1");
		mav.addObject("user", user);
		return mav;
	}
	
	@RequestMapping("test7")
	public String test7(@ModelAttribute User user) {
		return "forward:test8";
	}
	
	@RequestMapping("test8")
	public ModelAndView test8(User user) {
		return new ModelAndView("user/detail", "user", user);
	}
	
	@ModelAttribute
	@RequestMapping("test9")
	public User test9(@SessionAttribute User user) {
		user.setUserNick("updated");
		return user;
	}
	
	@ModelAttribute("userdetail")
	public User test10(@RequestParam String userId) {
		System.out.println("test10()");
		return userRepository.getOne(userId);
	}
	
	@ModelAttribute("users")
	public List<User> users(){
		System.out.println("users()");
		return userRepository.findAll();
	}
	
}
