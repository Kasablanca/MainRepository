package cn.lm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.lm.ResultObject;
import cn.lm.model.User;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req, User user, RedirectAttributes redirectAttr) {
		ModelAndView mav = new ModelAndView();
		System.out.println(user);
		if("MR.RIGHT".equals(user.getUsername()) && 20 == user.getAge()) {
			req.getSession().setAttribute("user", user);
			
			Map<String,?> attr = redirectAttr.getFlashAttributes();
			Object orignUrl = attr.get("orignUrl");
			if(StringUtils.isNotBlank((CharSequence) orignUrl)) {
				mav.setViewName("redirect:" + orignUrl);
			} else {
				mav.setViewName("redirect:user/home");
			}
			mav.setViewName("redirect:user/home");
		} else {
			mav.addObject("result", new ResultObject("1", "用户名或密码不正确"));
			mav.setViewName("forward:/login.jsp");
		}
		
		return mav;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		req.getSession().invalidate();
		
		return "redirect:/login.jsp";
	}
	
}
