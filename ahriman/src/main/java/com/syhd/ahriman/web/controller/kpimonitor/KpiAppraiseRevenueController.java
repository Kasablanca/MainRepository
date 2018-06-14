package com.syhd.ahriman.web.controller.kpimonitor;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.KpiAppraiseRevenueService;
import com.syhd.ahriman.utils.DateUtils;

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
	
	@Autowired
	private CacheManager cacheManager;

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
	
	/**
	 * 每次服务器启动时异步执行KPI收入信息的查询任务
	 */
	@PostConstruct
	private void init() {
		cacheManager.getCache("KpiAppraiseRevenue").clear(); // 手动清除缓存
		List<AppServer> serverList = appServerService.getAllServer();
		Date yesterday = DateUtils.getYesterdayTime0(); // 昨天凌晨00:00:00
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		for(AppServer server : serverList) {
			kpiAppraiseRevenueService.initFetch(server,yesterday,endDate);
		}
	}
	
}
