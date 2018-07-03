package com.min.someapp.dao.mapper;

import com.min.someapp.dao.model.LoggingEventException;
import com.min.someapp.dto.Sort;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LoggingEventExceptionMapper {
    int deleteByPrimaryKey(@Param("eventId") Long eventId, @Param("i") Short i);

    int insert(LoggingEventException record);

    int insertSelective(LoggingEventException record);

    LoggingEventException selectByPrimaryKey(@Param("eventId") Long eventId, @Param("i") Short i);

    int updateByPrimaryKeySelective(LoggingEventException record);

    int updateByPrimaryKey(LoggingEventException record);

	List<LoggingEventException> getRecordList(@Param("eventId")Long eventId, @Param("sort")Sort sort);
}