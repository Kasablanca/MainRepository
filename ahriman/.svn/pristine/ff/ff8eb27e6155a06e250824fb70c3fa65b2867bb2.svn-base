package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.GeneralOnlineInfo;
import com.syhd.ahriman.dto.GeneralOnlineInfoVO;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;

public interface GeneralOnlineInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GeneralOnlineInfo record);

    int insertSelective(GeneralOnlineInfo record);

    GeneralOnlineInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GeneralOnlineInfo record);

    int updateByPrimaryKey(GeneralOnlineInfo record);

	List<GeneralOnlineInfoVO> getStatisticWithUserRegistered(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);

	Long getStatisticCountWithUserRegistered(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);

	List<GeneralOnlineInfoVO> getStatistic(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);

	Long getStatisticCount(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);

	List<String> getAllPlatform();

	Date getLastCountDate(Integer serverid);

	void batchInsert(
			@Param("records")List<GeneralOnlineInfo> recordList, 
			@Param("storedTable")String storedTable);
}