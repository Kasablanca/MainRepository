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
import com.syhd.ahriman.service.impl.PlayerRemainService;

@Controller
@RequestMapping("playerRemain")
public class PlayerRemainController {

	@Autowired
	private PlayerRemainService playerRemainService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("importantMonitor/playerremain/index");
		mav.addObject("platform", playerRemainService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("getPlayerRemain")
	public TableData getPlayerRemain(RequestPayload param,PageAndSort pageAndSort){
		return playerRemainService.getStatistic(param,pageAndSort);
	}
	
}
