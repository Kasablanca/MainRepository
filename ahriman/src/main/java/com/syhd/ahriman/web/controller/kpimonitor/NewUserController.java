package com.syhd.ahriman.web.controller.kpimonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.NewUserService;

/**
 * 新增用户统计
 * @author MIN.LEE
 *
 */
@RestController
@RequestMapping("newUser")
public class NewUserController {

	@Autowired
	private NewUserService newUserService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("importantMonitor/newuser/index");
		mav.addObject("platform", newUserService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@RequestMapping("getDailyNewUser")
	public Result getDailyNewUser(RequestPayload param){
		return newUserService.getStatistic(param);
	}
	
}
