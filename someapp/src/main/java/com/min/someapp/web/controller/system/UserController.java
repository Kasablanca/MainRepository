package com.min.someapp.web.controller.system;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.min.someapp.dao.model.User;
import com.min.someapp.dto.PageAndSort;
import com.min.someapp.dto.Result;
import com.min.someapp.dto.TableData;
import com.min.someapp.service.system.AuthorityGroupService;
import com.min.someapp.service.system.UserService;
import com.min.someapp.utils.HttpUtils;
import com.min.someapp.utils.ValidationUtils;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityGroupService authorityGroupService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("system/user/index");
	}
	
	@RequestMapping("list")
	public TableData list(User filter,PageAndSort pageAndSort) {
		return userService.getList(filter, pageAndSort);
	}
	
	@RequestMapping("insertPage")
	public ModelAndView insertPage() {
		return new ModelAndView("system/user/insert","authorityGroups",authorityGroupService.getAllRecord());
	}
	
	@RequestMapping("insert")
	public Result insert(@Validated User target,BindingResult error,String authorityGroupIds) {
		Integer optAccId = HttpUtils.getUserIdInSession(httpSession);
		target.setAddAccId(optAccId);
		target.setUpdAccId(optAccId);
		return ValidationUtils.decideResult(error, userService.insert(target,authorityGroupIds));
	}
	
	@RequestMapping("updatePage")
	public ModelAndView updatePage(Integer id) {
		ModelAndView mav = new ModelAndView("system/user/update");
		mav.addObject("model", userService.getById(id));
		mav.addObject("authorityGroups", authorityGroupService.getAllRecord());
		mav.addObject("authorityGroupIds", userService.getAuthorityGroupId(id));
		
		return mav;
	}
	
	@RequestMapping("update")
	public Result update(@Validated User target,BindingResult error,String authorityGroupIds) {
		Integer optAccId = HttpUtils.getUserIdInSession(httpSession);
		target.setUpdAccId(optAccId);
		return ValidationUtils.decideResult(error, userService.update(target,authorityGroupIds));
	}
	
	@RequestMapping("delete")
	public Result delete(User target) {
		return userService.delete(target);
	}
	
}
