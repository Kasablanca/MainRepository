package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.PlayerRemain;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;

public interface PlayerRemainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlayerRemain record);

    int insertSelective(PlayerRemain record);

    PlayerRemain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlayerRemain record);

    int updateByPrimaryKey(PlayerRemain record);
    
    List<PlayerRemain> getStatistic(@Param("param")RequestPayload payload,@Param("pageAndSort")PageAndSort pageAndSort);
    
    Long getStatisticCount(@Param("param")RequestPayload payload,@Param("pageAndSort")PageAndSort pageAndSort);
    
    List<PlayerRemain> getMixinStatistic(@Param("param")RequestPayload payload,@Param("pageAndSort")PageAndSort pageAndSort);

	Long getMixinStatisticCount(@Param("param")RequestPayload payload,@Param("pageAndSort")PageAndSort pageAndSort);

	List<String> getAllPlatform();
	
	/**
	 * 获取服务器统计的最近日期
	 * @param serverId 服务器ID
	 * @return 最近日期
	 */
	Date getLastCountDate(Integer serverId);
	
	/**
	 * 批量添加新增角色统计数据
	 * @param recordList
	 */
	void batchInsert(@Param("records")List<PlayerRemain> recordList,@Param("storedTable")String storedTable);
	
	/**
	 * 获取没完成统计的最早日期
	 */
	Date getEarlyDate();
	
	/**
	 * 根据服务器ID、平台和日期更新留存率
	 * @param record 使用非空值更新
	 * @return 影响记录数
	 */
	int updateByUniqueKeySelective(PlayerRemain record);

	void createTempTable();
	
}