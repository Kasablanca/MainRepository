package com.min.someapp.web.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.min.someapp.dao.model.LoggingEvent;
import com.min.someapp.dto.PageAndSort;
import com.min.someapp.dto.Sort;
import com.min.someapp.dto.TableData;
import com.min.someapp.service.system.LoggerService;

@Component
@WebService(serviceName="LogService")
public class LogServiceEndpoint extends SpringBeanAutowiringSupport {
	
	@Autowired
	private LoggerService loggerService;
	
	@WebMethod
	public TableData getEventList(LoggingEvent filter,PageAndSort pageAndSort) {
		return loggerService.getEventList(filter, pageAndSort);
	}
	
	@WebMethod
	public TableData getEventPropertyList(Long eventId, Sort sort) {
		return loggerService.getEventExceptionList(eventId, sort);
	}
	
	@WebMethod
	public TableData getEventExceptionList(Long eventId, Sort sort) {
		return loggerService.getEventExceptionList(eventId, sort);
	}
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:40000/logService", new LogServiceEndpoint());
        System.out.println("server is listening...");
	}

}
