package com.syhd.ahriman.web.controller.system;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syhd.ahriman.dao.model.AuthorityGroup;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.AuthorityGroupService;
import com.syhd.ahriman.service.impl.AuthorityService;
import com.syhd.ahriman.utils.HttpUtils;
import com.syhd.ahriman.utils.ValidationUtils;

@RestController
@RequestMapping("authorityGroup")
public class AuthorityGroupController {

	@Autowired
	private AuthorityGroupService authorityGroupService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private ObjectMapper jackson;
	
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("system/authoritygroup/index");
	}
	
	@RequestMapping("list")
	public TableData list(AuthorityGroup filter,PageAndSort pageAndSort) {
		return authorityGroupService.getRecordList(filter, pageAndSort);
	}
	
	@RequestMapping("insertPage")
	public ModelAndView insertPage(Integer id) throws JsonProcessingException {
		String authorityTree = jackson.writeValueAsString(authorityService.getDescendant(null).getData());
		return new ModelAndView("system/authoritygroup/insert","authorityTree",authorityTree);
	}
	
	@RequestMapping("insert")
	public Result insert(@Validated AuthorityGroup record,BindingResult error,String authorityIds) {
		Integer optAccId = HttpUtils.getUserIdInSession(httpSession);
		record.setAddAccId(optAccId);
		record.setUpdAccId(optAccId);
		
		return ValidationUtils.determineResult(error, authorityGroupService.insert(record,authorityIds));
	}
	
	@RequestMapping("updatePage")
	public ModelAndView updatePage(Integer id) throws JsonProcessingException {
		String authorityTree = jackson.writeValueAsString(authorityService.getDescendant(null).getData());
		String authorityIds = jackson.writeValueAsString(authorityGroupService.getAuthoritiesId(id));
		
		ModelAndView mav = new ModelAndView("system/authoritygroup/update");
		mav.addObject("model", authorityGroupService.getById(id));
		mav.addObject("authorityTree", authorityTree);
		mav.addObject("authorityIds", authorityIds);
		return mav;
	}
	
	@RequestMapping("update")
	public Result update(@Validated AuthorityGroup record,BindingResult error,String authorityIds) {
		Integer optAccId = HttpUtils.getUserIdInSession(httpSession);
		record.setUpdAccId(optAccId);
		return ValidationUtils.determineResult(error, authorityGroupService.update(record,authorityIds));
	}
	
	@RequestMapping("delete")
	public Result delete(AuthorityGroup authorityGroup) {
		return authorityGroupService.delete(authorityGroup);
	}
	
}
