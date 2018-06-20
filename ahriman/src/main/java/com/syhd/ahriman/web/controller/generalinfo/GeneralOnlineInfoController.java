package com.syhd.ahriman.web.controller.generalinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.GeneralOnlineInfoService;

/**
 * 综合在线数据
 * @author MIN.LEE
 *
 */
@Controller
@RequestMapping("generalInfo/generalOnlineInfo")
public class GeneralOnlineInfoController {

	@Autowired
	private GeneralOnlineInfoService generalOnlineInfoService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("generalInfo/generalOnlineInfo/index");
		mav.addObject("platform", generalOnlineInfoService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("getGeneralOnlineInfo")
	public TableData getGeneralOnlineInfo(RequestPayload param,PageAndSort pageAndSort){
		return generalOnlineInfoService.getStatistic(param,pageAndSort);
	}
	
}
