package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.DailyNewRole;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;

public interface DailyNewRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DailyNewRole record);

    int insertSelective(DailyNewRole record);

    DailyNewRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DailyNewRole record);

    int updateByPrimaryKey(DailyNewRole record);
    
    /**
     * 获取新增角色统计数据
     * @param param 查询参数
     * @return 新增角色统计数据
     */
    List<KpiStatistic> getStatistic(@Param("param")RequestPayload param);

	/**
	 * 获取所有的平台
	 * @return 所有的平台的列表
	 */
	List<String> getAllPlatform();
	
	/**
	 * 创建存放今天新增用户统计数据的临时表
	 */
	void createTodayNewRoleTable();

	/**
	 * 插入临时表
	 * @param record 今天的新增角色统计数据
	 * @return 影响记录数
	 */
	int insertIntoTemp(DailyNewRole record);

	/**
	 * 获取混合今天和之前的新增角色统计数据
	 * @param param 查询参数
	 * @return 新增角色统计数据
	 */
	List<KpiStatistic> getMixinStatistic(@Param("param")RequestPayload param);

	/**
	 * 获取服务器统计的最近日期
	 * @param serverId 服务器ID
	 * @return 最近进入
	 */
	Date getLastCountDate(Integer serverId);

	/**
	 * 批量添加新增角色统计数据
	 * @param recordList
	 */
	void batchInsert(@Param("records")List<DailyNewRole> recordList);
}