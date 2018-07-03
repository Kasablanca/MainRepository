package com.min.someapp.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.min.someapp.dao.model.LoggingEvent;
import com.min.someapp.dto.PageAndSort;
import com.min.someapp.dto.Sort;
import com.min.someapp.dto.TableData;
import com.min.someapp.service.system.LoggerService;

@RestController
@RequestMapping("log")
public class LoggerController {
	
	@Autowired
	private LoggerService loggerService;

	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("system/log/index");
	}
	
	@RequestMapping("eventList")
	public TableData eventList(LoggingEvent filter,PageAndSort pageAndSort) {
		return loggerService.getEventList(filter, pageAndSort);
	}
	
	@RequestMapping("eventPropertyPage")
	public ModelAndView eventPropertyPage() {
		return new ModelAndView("system/log/mdc");
	}
	
	@RequestMapping("eventPropertyList")
	public TableData eventPropertyList(Long eventId, Sort sort) {
		return loggerService.getEventPropertyList(eventId, sort);
	}
	
	@RequestMapping("eventExceptionPage")
	public ModelAndView eventExceptionPage() {
		return new ModelAndView("system/log/exception");
	}
	
	@RequestMapping("eventExceptionList")
	public TableData eventExceptionList(Long eventId, Sort sort) {
		return loggerService.getEventExceptionList(eventId, sort);
	}
	
}
