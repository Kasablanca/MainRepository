package com.syhd.ahriman.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.DailyRevenueRequestParam;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.KpiAppraiseRevenueService;

/**
 * KPI考核收入
 * @author MIN.LEE
 *
 */
@RestController
@RequestMapping("kpiAppraiseRevenue")
public class KpiAppraiseRevenueController {
	
	@Autowired
	private KpiAppraiseRevenueService kpiAppraiseRevenueService;

	@RequestMapping("index")
	public ModelAndView home(){
		ModelAndView mav = new ModelAndView("importantMonitor/kpiAppraiseRevenue/index");
		mav.addObject("platform", kpiAppraiseRevenueService.getAllPlatform());
		mav.addObject("serverList", kpiAppraiseRevenueService.getAllServer());
		
		return mav;
	}
	
	@RequestMapping("getDailyRevenue")
	public Result getDailyRevenue(DailyRevenueRequestParam param){
		return kpiAppraiseRevenueService.getDailyRevenue(param);
	}
	
}
