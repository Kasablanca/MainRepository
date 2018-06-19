package com.syhd.ahriman.web.controller.kpimonitor;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.KpiUserLTVService;
import com.syhd.ahriman.utils.DateUtils;

@Controller
@RequestMapping("userltv")
public class KpiUserLTVController {

	@Autowired
	private KpiUserLTVService kpiUserLTVService;
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("importantMonitor/kpiuserltv/index");
		mav.addObject("platform", kpiUserLTVService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("getKpiUserLtv")
	public TableData getKpiUserLtv(RequestPayload param,PageAndSort pageAndSort){
		return kpiUserLTVService.getStatistic(param,pageAndSort);
	}
	
	/**
	 * 每次服务器启动时异步执行活跃用户数的查询任务
	 */
	@PostConstruct
	private void init() {
		cacheManager.getCache("userltv").clear();		// 手动清除缓存
		List<AppServer> serverList = appServerService.getAllServer();
		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		for(AppServer server : serverList) {
			kpiUserLTVService.initFetch(server,yesterday,endDate);
		}
	}
	
}