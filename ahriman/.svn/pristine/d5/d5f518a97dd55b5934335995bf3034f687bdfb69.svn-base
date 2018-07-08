package com.syhd.ahriman.dao.mapper;

import com.syhd.ahriman.dao.model.UserRegistered;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserRegisteredMapper {
    int deleteByPrimaryKey(@Param("date") Date date, @Param("platform") String platform);

    int insert(UserRegistered record);

    int insertSelective(UserRegistered record);

    UserRegistered selectByPrimaryKey(@Param("date") Date date, @Param("platform") String platform);

    int updateByPrimaryKeySelective(UserRegistered record);

    int updateByPrimaryKey(UserRegistered record);

    /**
     * 获取上一次统计的日期
     * @return 统计日期
     */
	Date getLastCountDate();

	/**
	 * 获取每日累计注册数
	 * @param startDate 开始日期,可为空
	 * @param endDate 结束日期
	 * @return 查询列表
	 */
	List<UserRegistered> getUserRegistered(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
	
	/**
	 * 批量插入
	 * @param list 待插入数据
	 */
	void batchInsert(@Param("list")List<UserRegistered> list);
}