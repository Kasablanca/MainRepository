package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.OnlineCount;
import com.syhd.ahriman.dto.RequestPayload;

public interface OnlineCountMapper {
    int insert(OnlineCount record);

    int insertSelective(OnlineCount record);

	List<OnlineCount> getStatistic(@Param("param")RequestPayload copy);

	List<String> getAllPlatform();

	Date getLastCountDate(Integer serverid);

	void batchInsert(@Param("records")List<OnlineCount> recordList, @Param("storedTable")String storedTable);
}