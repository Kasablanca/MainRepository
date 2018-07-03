package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.OnlineTable;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;

public interface OnlineTableMapper {
    int insert(OnlineTable record);

    int insertSelective(OnlineTable record);

	List<OnlineTable> getStatistic(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);
	
	Long getStatisticCount(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	List<String> getAllPlatform();

	Date getLastCountDate(Integer serverid);

	void batchInsert(@Param("records")List<OnlineTable> recordList, @Param("storedTable")String storedTable);
}