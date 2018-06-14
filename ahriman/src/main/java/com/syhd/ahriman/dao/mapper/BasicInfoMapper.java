package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.BasicInfo;
import com.syhd.ahriman.dto.BasicInfoVO;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;

public interface BasicInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BasicInfo record);

    int insertSelective(BasicInfo record);

    BasicInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BasicInfo record);

    int updateByPrimaryKey(BasicInfo record);

	List<BasicInfoVO> getStatistic(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	Long getStatisticCount(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	List<String> getAllPlatform();

	//void createTodayKpiPayTable();

	//void batchInsertTemp(List<BasicInfo> recordList);

	//List<BasicInfoVO> getMixinStatistic(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);

	//Long getMixinStatisticCount(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);

	Date getLastCountDate(Integer serverid);

	void batchInsert(@Param("records")List<BasicInfo> recordList);
}