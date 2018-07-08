package com.syhd.ahriman.web.controller.system;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.AuthorityGroupService;
import com.syhd.ahriman.service.impl.UserService;
import com.syhd.ahriman.utils.HttpUtils;
import com.syhd.ahriman.utils.ValidationUtils;

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
		return userService.getUserList(filter, pageAndSort);
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
		return ValidationUtils.determineResult(error, userService.insert(target,authorityGroupIds));
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
		return ValidationUtils.determineResult(error, userService.update(target,authorityGroupIds));
	}
	
	@RequestMapping("delete")
	public Result delete(User target) {
		return userService.delete(target);
	}
	
}
