package com.syhd.ahriman.web.controller.generalinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.RealtimeOnlineTableService;

/**
 * 实时在线表
 * @author MIN.LEE
 *
 */
//@Controller
@RequestMapping("generalInfo/realTimeOnlineTable")
public class RealtimeOnlineTableController {

	@Autowired
	private RealtimeOnlineTableService RealtimeOnlineTableService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("generalInfo/realTimeOnlineTable/index");
		mav.addObject("platform", RealtimeOnlineTableService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("getRealTimeOnline")
	public Result getRealTimeOnline(RequestPayload param,PageAndSort pageAndSort){
		return RealtimeOnlineTableService.getStatistic(param,pageAndSort);
	}
	
}
