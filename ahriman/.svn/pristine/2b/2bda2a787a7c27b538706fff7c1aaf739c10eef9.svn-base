package com.syhd.ahriman.web.controller.generalinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.NewValidUserService;

/**
 * 新增有效用户
 * @author MIN.LEE
 *
 */
@Controller
@RequestMapping("generalInfo/newValidUser")
public class NewValidUserController {

	@Autowired
	private NewValidUserService newValidUserService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("generalInfo/newValidUser/index");
		mav.addObject("platform", newValidUserService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("getNewValidUser")
	public TableData getNewValidUser(RequestPayload param,PageAndSort pageAndSort){
		return newValidUserService.getStatistic(param,pageAndSort);
	}
	
}
