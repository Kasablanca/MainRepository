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
import com.syhd.ahriman.service.impl.NewUserService;
import com.syhd.ahriman.utils.DateUtils;

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
	
	@Autowired
	private CacheManager cacheManager;

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
	
	/**
	 * 每次服务器启动时异步执行活跃用户数的查询任务
	 */
	@PostConstruct
	private void init() {
		cacheManager.getCache("newuser").clear();		// 手动清除缓存
		List<AppServer> serverList = appServerService.getAllServer();
		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		for(AppServer server : serverList) {
			newUserService.initFetch(server,yesterday,endDate);
		}
	}
	
}
