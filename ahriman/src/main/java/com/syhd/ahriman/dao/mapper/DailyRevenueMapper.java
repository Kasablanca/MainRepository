package com.syhd.ahriman.dao.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.DailyRevenue;
import com.syhd.ahriman.dto.DailyRevenueRequestParam;
import com.syhd.ahriman.dto.DailyRevenueVO;
import com.syhd.ahriman.dto.ExchangeRate;

public interface DailyRevenueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DailyRevenue record);

    int insertSelective(DailyRevenue record);

    DailyRevenue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DailyRevenue record);

    int updateByPrimaryKey(DailyRevenue record);

    /**
     * 获取KPI考核收入
     * @param param 包括服务器、平台等参数
     * @param rate 汇率
     * @return 统计结果
     */
    List<DailyRevenueVO> getDailyRevenue(
    		@Param("param")DailyRevenueRequestParam param,
    		@Param("rate")ExchangeRate rate);
    
    /**
     * 获取服务器统计截止日期
     * @param serverId 服务器ID
     * @return 截止日期
     */
    Date getEndDateByServerId(Integer serverId);
    
    /**
     * 获取所有的服务器
     * @return 包括服务器ID和服务器名(serverId,serverName)的列表
     */
    List<DailyRevenue> getAllServer();
    
    /**
     * 获取所有的平台
     * @return 平台名称的列表
     */
    List<String> getAllPlatform();
    
    /**
     * 创建名为"t_today_revenue"的临时表
     */
    void createTodayRevenueTable();
    
    /**
     * 插入数据到临时表
     * @param record 每个服务器今天截止现在的收入
     * @return 影响记录数
     */
    int insertIntoTemp(DailyRevenue record);
    
    /**
     * 获取今天和之前的混合KPI考核收入
     * @param param 查询参数
     * @param rate 汇率
     * @return 统计结果
     */
    List<DailyRevenueVO> getMixinDailyRevenue(
    		@Param("param")DailyRevenueRequestParam param,
    		@Param("rate")ExchangeRate rate);
    
    /**
     * 批量插入每个服务器、每天、每个平台、每种货币的收益
     * @param records 需要插入的数据
     */
    void batchInsert(@Param("records")Collection<DailyRevenue> records);
}