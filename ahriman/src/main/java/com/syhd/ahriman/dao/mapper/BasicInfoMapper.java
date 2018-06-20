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

    /**
     * 获取基础数据表
     * @param copy 查询参数，包括了服务器ID
     * @param pageAndSort 排序和分页参数
     * @return 未包含用户注册数信息
     */
	List<BasicInfoVO> getStatistic(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);

	Long getStatisticCount(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);
	
	/**
     * 获取基础数据表
     * @param copy 查询参数，不包括服务器ID
     * @param pageAndSort 排序和分页参数
     * @return 包含用户注册数信息
     */
	List<BasicInfoVO> getStatisticWithUserRegistered(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);
	
	Long getStatisticCountWithUserRegistered(
			@Param("param")RequestPayload copy, 
			@Param("pageAndSort")PageAndSort pageAndSort);

	List<String> getAllPlatform();

	//void createTodayKpiPayTable();

	//void batchInsertTemp(List<BasicInfo> recordList);

	//List<BasicInfoVO> getMixinStatistic(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);

	//Long getMixinStatisticCount(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);

	Date getLastCountDate(Integer serverid);

	void batchInsert(@Param("records")List<BasicInfo> recordList, @Param("storedTable")String storedTable);
	
}