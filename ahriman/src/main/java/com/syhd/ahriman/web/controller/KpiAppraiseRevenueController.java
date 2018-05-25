package com.syhd.ahriman.web.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dto.DailyRevenueRequestParam;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.AppServerService;
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
	
	@Autowired
	private AppServerService appServerService;

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
	
	/**
	 * 每次服务器启动时异步执行KPI收入信息的查询任务
	 */
	@PostConstruct
	private void init() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			kpiAppraiseRevenueService.preFetch(server);
		}
	}
	
}
