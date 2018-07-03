package com.syhd.ahriman.web.controller.kpimonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.KpiAppraiseRevenueService;

/**
 * KPI考核收入统计
 * @author MIN.LEE
 *
 */
@RestController
@RequestMapping("kpiAppraiseRevenue")
public class KpiAppraiseRevenueController {
	
	@Autowired
	private KpiAppraiseRevenueService kpiAppraiseRevenueService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView home(){
		ModelAndView mav = new ModelAndView("importantMonitor/kpiAppraiseRevenue/index");
		mav.addObject("platform", kpiAppraiseRevenueService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@RequestMapping("getDailyRevenue")
	public Result getDailyRevenue(RequestPayload param){
		return kpiAppraiseRevenueService.getDailyRevenue(param);
	}
	
}
