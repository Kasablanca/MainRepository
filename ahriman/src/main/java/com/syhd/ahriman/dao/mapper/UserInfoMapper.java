package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.UserInfo;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    /**
     * 获取新增账户统计数据
     * @param param 查询参数
     * @return 新增账户统计数据
     */
    List<KpiStatistic> getStatistic(@Param("param")RequestPayload param);
    
    /**
     * 
     * @param startDate 开始日期
     * @return 记录数
     */
    int getCountByDate(Date startDate);
}