package com.min.someapp.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.min.someapp.dao.model.LoggingEvent;
import com.min.someapp.dto.PageAndSort;

public interface LoggingEventMapper {
    int deleteByPrimaryKey(Long eventId);

    int insert(LoggingEvent record);

    int insertSelective(LoggingEvent record);

    LoggingEvent selectByPrimaryKey(Long eventId);

    int updateByPrimaryKeySelective(LoggingEvent record);

    int updateByPrimaryKeyWithBLOBs(LoggingEvent record);

    int updateByPrimaryKey(LoggingEvent record);

	List<LoggingEvent> getRecordList(@Param("filter")LoggingEvent filter, @Param("pageAndSort")PageAndSort pageAndSort);

	long getRecordListCount(@Param("filter")LoggingEvent filter, @Param("pageAndSort")PageAndSort pageAndSort);
}