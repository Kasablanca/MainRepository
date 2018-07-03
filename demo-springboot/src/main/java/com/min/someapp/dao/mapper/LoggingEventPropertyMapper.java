package com.min.someapp.dao.mapper;

import com.min.someapp.dao.model.LoggingEventProperty;
import com.min.someapp.dto.Sort;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LoggingEventPropertyMapper {
    int deleteByPrimaryKey(@Param("eventId") Long eventId, @Param("mappedKey") String mappedKey);

    int insert(LoggingEventProperty record);

    int insertSelective(LoggingEventProperty record);

    LoggingEventProperty selectByPrimaryKey(@Param("eventId") Long eventId, @Param("mappedKey") String mappedKey);

    int updateByPrimaryKeySelective(LoggingEventProperty record);

    int updateByPrimaryKeyWithBLOBs(LoggingEventProperty record);

	List<LoggingEventProperty> getRecordList(@Param("eventId")Long eventId, @Param("sort")Sort sort);
}