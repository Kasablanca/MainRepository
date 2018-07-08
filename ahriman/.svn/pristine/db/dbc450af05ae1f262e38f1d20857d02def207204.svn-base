package com.syhd.ahriman.web.controller.kpimonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.KpiUserLTVService;

@Controller
@RequestMapping("userltv")
public class KpiUserLTVController {

	@Autowired
	private KpiUserLTVService kpiUserLTVService;
	
	@Autowired
	private AppServerService appServerService;

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
	
}
