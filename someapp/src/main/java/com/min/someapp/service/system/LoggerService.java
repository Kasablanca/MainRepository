package com.min.someapp.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.someapp.dao.mapper.LoggingEventExceptionMapper;
import com.min.someapp.dao.mapper.LoggingEventMapper;
import com.min.someapp.dao.mapper.LoggingEventPropertyMapper;
import com.min.someapp.dao.model.LoggingEvent;
import com.min.someapp.dao.model.LoggingEventException;
import com.min.someapp.dao.model.LoggingEventProperty;
import com.min.someapp.dto.PageAndSort;
import com.min.someapp.dto.Sort;
import com.min.someapp.dto.TableData;

@Service
public class LoggerService {
	
	@Autowired
	private LoggingEventMapper loggingEventMapper;
	
	@Autowired
	private LoggingEventPropertyMapper loggingEventPropertyMapper;
	
	@Autowired
	private LoggingEventExceptionMapper loggingEventExceptionMapper;
	
	public TableData getEventList(LoggingEvent filter,PageAndSort pageAndSort) {
		//filter.setLoggerName(filter.getLoggerName().replaceAll("'", "\\\'"));
		List<LoggingEvent> list = loggingEventMapper.getRecordList(filter,pageAndSort);
		long count = loggingEventMapper.getRecordListCount(filter,pageAndSort);
		
		TableData result = new TableData();
		result.setCode(0);
		result.setCount(count);
		result.setData(list);
		
		return result;
	}
	
	public TableData getEventPropertyList(Long eventId, Sort sort) {
		List<LoggingEventProperty> list = loggingEventPropertyMapper.getRecordList(eventId,sort);
		
		TableData result = new TableData();
		result.setCode(0);
		result.setCount((long) list.size());
		result.setData(list);
		
		return result;
	}
	
	public TableData getEventExceptionList(Long eventId, Sort sort) {
		List<LoggingEventException> list = loggingEventExceptionMapper.getRecordList(eventId,sort);
		
		TableData result = new TableData();
		result.setCode(0);
		result.setCount((long) list.size());
		result.setData(list);
		
		return result;
	}
	
}
