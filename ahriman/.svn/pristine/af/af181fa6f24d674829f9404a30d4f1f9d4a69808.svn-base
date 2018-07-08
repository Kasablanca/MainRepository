package com.syhd.ahriman.web.controller.generalinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.RealtimeOnlineChartService;

/**
 * 实时在线图
 * @author MIN.LEE
 *
 */
@Controller
@RequestMapping("generalInfo/realTimeOnlineChart")
public class RealtimeOnlineChartController {

	@Autowired
	private RealtimeOnlineChartService realtimeOnlineChartService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("generalInfo/realTimeOnlineChart/index");
		mav.addObject("platform", realtimeOnlineChartService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("getRealTimeOnline")
	public Result getRealTimeOnline(RequestPayload param){
		return realtimeOnlineChartService.getStatistic(param);
	}
	
}
