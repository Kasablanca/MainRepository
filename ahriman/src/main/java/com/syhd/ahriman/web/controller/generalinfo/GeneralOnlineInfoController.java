package com.syhd.ahriman.web.controller.generalinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.service.impl.AppServerService;
import com.syhd.ahriman.service.impl.BasicInfoService;

/**
 * 综合在线数据
 * @author MIN.LEE
 *
 */
@Controller
@RequestMapping("generalInfo/generalOnlineInfo")
public class GeneralOnlineInfoController {

	@Autowired
	private BasicInfoService basicInfoService;
	
	@Autowired
	private AppServerService appServerService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("generalInfo/basicInfo/index");
		mav.addObject("platform", basicInfoService.getAllPlatform());
		mav.addObject("serverList", appServerService.getAllServer());
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("getBasicInfo")
	public TableData getBasicInfo(RequestPayload param,PageAndSort pageAndSort){
		return basicInfoService.getStatistic(param,pageAndSort);
	}
	
}
