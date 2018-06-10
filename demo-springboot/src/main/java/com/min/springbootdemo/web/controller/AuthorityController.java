package com.min.springbootdemo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.min.springbootdemo.dao.model.Authority;
import com.min.springbootdemo.dto.Result;
import com.min.springbootdemo.service.AuthorityService;

@RestController
@RequestMapping("authority")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;
	
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("system/authority/index");
	}
	
	@RequestMapping("getDescendant")
	public Result getDescendant(@RequestParam(value="id",required=false)Integer parentId) {
		return authorityService.getDescendant(parentId);
	}
	
	@RequestMapping("editPage")
	public ModelAndView editPage(Integer id) {
		if(id != null) {
			return new ModelAndView("system/authority/editPage", "model", authorityService.getById(id));
		} else {
			return new ModelAndView("system/authority/editPage");
		}
	}
	
	@RequestMapping("delete")
	public Result delete(Authority target) {
		return authorityService.delete(target);
	}
	
	@RequestMapping("insert")
	public Result insert(Authority target) {
		return authorityService.insert(target);
	}
	
	@RequestMapping("update")
	public Result update(Authority target) {
		return authorityService.update(target);
	}
	
}
