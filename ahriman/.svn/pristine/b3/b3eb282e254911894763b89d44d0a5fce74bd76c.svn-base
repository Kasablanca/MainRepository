package com.syhd.ahriman.web.controller.kpimonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.ActiveUserService;
import com.syhd.ahriman.service.impl.AppServerService;

/**
 * 活跃用户数统计
 * @author MIN.LEE
 *
 */
@RestController
@RequestMapping("activeUser")
public class ActiveUserController {
	
	@Autowired
	private ActiveUserService activeUserService;
	
	@Autowired
	private AppServerService appServerService;
	
	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("importantMonitor/activeuser/index");
		mav.addObject("platform", activeUserService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@RequestMapping("getDailyActiveUser")
	public Result getDailyActiveUser(RequestPayload param){
		return activeUserService.getDailyActiveUser(param);
	}
	
}
