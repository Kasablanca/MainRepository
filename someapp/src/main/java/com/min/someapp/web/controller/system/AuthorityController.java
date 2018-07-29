package com.min.someapp.web.controller.system;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.min.someapp.dao.model.Authority;
import com.min.someapp.dto.Result;
import com.min.someapp.service.system.AuthorityService;
import com.min.someapp.utils.HttpUtils;
import com.min.someapp.utils.ValidationUtils;
import com.min.someapp.web.validation.DeleteGroup;
import com.min.someapp.web.validation.InsertGroup;
import com.min.someapp.web.validation.UpdateGroup;

@RestController
@RequestMapping("authority")
public class AuthorityController {
	
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private AuthorityService authorityService;
	
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("system/authority/index");
	}
	
	@RequestMapping("treedata")
	public Result getDescendant(@RequestParam(name="id",required=false)Integer parentId) {
		return authorityService.getDescendant(parentId);
	}
	
	@RequestMapping("insertPage")
	public ModelAndView insertPage(Integer parentId) {
		return new ModelAndView("system/authority/insert","parentId",parentId);
	}
	
	@RequestMapping("insert")
	public Result insert(@Validated(InsertGroup.class) Authority target,BindingResult error) {
		Integer optAccId = HttpUtils.getUserIdInSession(httpSession);
		target.setAddAccId(optAccId);
		target.setUpdAccId(optAccId);
		return ValidationUtils.decideResult(error, authorityService.insert(target));
	}
	
	@RequestMapping("updatePage")
	public ModelAndView updatePage(Integer id) {
		return new ModelAndView("system/authority/update","model",authorityService.findById(id));
	}
	
	@RequestMapping("update")
	public Result update(@Validated(UpdateGroup.class) Authority target,BindingResult error) {
		Integer optAccId = HttpUtils.getUserIdInSession(httpSession);
		target.setUpdAccId(optAccId);
		return ValidationUtils.decideResult(error, authorityService.update(target));
	}
	
	@RequestMapping("delete")
	public Result delete(@Validated(DeleteGroup.class) Authority target) {
		return authorityService.delete(target);
	}
	
}
