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
import com.syhd.ahriman.service.impl.ActiveUserService;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.utils.DateUtils;

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
	
	@Autowired
	private CacheManager cacheManager;

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
	
	/**
	 * 每次服务器启动时异步执行活跃用户数的查询任务
	 */
	@PostConstruct
	private void init() {
		cacheManager.getCache("activeuser").clear();		// 手动清除缓存
		List<AppServer> serverList = appServerService.getAllServer();
		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		for(AppServer server : serverList) {
			activeUserService.initFetch(server,yesterday,endDate);
		}
	}
	
}
