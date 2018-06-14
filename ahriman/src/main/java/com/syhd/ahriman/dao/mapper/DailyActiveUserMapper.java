package com.syhd.ahriman.dao.mapper;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.DailyActiveUser;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;

public interface DailyActiveUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DailyActiveUser record);

    int insertSelective(DailyActiveUser record);

    DailyActiveUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyActiveUser record);

    int updateByPrimaryKey(DailyActiveUser record);
    
    /**
     * 每日活跃用户统计
     * @param param 查询参数
     * @return 每日活跃用户统计
     */
	List<KpiStatistic> getStatistic(@Param("param")RequestPayload param);

	/**
	 * 获取所有服务器
	 * @return 包括服务器ID、服务器名称
	 */
	List<DailyActiveUser> getAllServer();

	/**
	 * 获取所有平台
	 * @return 所有平台的名称列表
	 */
	List<String> getAllPlatform();
	
	/**
	 * 创建存放今天活跃用户的临时表
	 */
	void createTodayActiveuserTable();

	/**
	 * 插入临时表
	 * @param record 今天的活跃用户数
	 * @return 影响记录数
	 */
	int insertIntoTemp(DailyActiveUser record);

	/**
	 * 获取今天和之前的活跃用户数据
	 * @param param 查询参数
	 * @return 活跃用户数据
	 */
	List<KpiStatistic> getMixinStatistic(@Param("param")RequestPayload param);

	/**
	 * 获取活跃用户统计的截止日期
	 * @param serverid 服务器ID
	 * @return 该服务器的活跃用户统计截止日期
	 */
	Date getEndDateByServerId(Integer serverid);

	/**
	 * 批量插入活跃用户数据
	 * @param recordList 活跃用户数据
	 */
	void batchInsert(@Param("records")List<DailyActiveUser> recordList);
}