package com.min.someapp.web.controller.system;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.min.someapp.dao.model.User;
import com.min.someapp.service.system.UserService;
import com.min.someapp.web.security.AuthorityInterceptor;

@Controller
public class FacadeController {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="login")
	public ModelAndView login(Boolean error) {
		return new ModelAndView("login", "error", error != null && error.equals(true));
	}

	@RequestMapping("home")
	public ModelAndView home() {
		User user = (User) httpSession.getAttribute(AuthorityInterceptor.USER_IN_SESSION);
		return new ModelAndView("home","authorities",userService.getAuthoritiesUri(user.getId()));
	}
	
	@RequestMapping("dashboard")
	public String dashboard() {
		return "dashboard";
	}
	
	@RequestMapping("errorPage")
	public String errorPage() {
		return "errorPage";
	}
	
}
