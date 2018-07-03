package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.NewValidUser;
import com.syhd.ahriman.dto.NewValidUserVO;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;

public interface NewValidUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewValidUser record);

    int insertSelective(NewValidUser record);

    NewValidUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewValidUser record);

    int updateByPrimaryKey(NewValidUser record);

	List<NewValidUserVO> getStatistic(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	Long getStatisticCount(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);
	
	List<String> getAllPlatform();

	Date getLastCountDate(@Param("serverId")Integer serverId);

	void batchInsert(@Param("records")List<NewValidUser> recordList, @Param("storedTable")String storedTable);

	List<NewValidUserVO> getMixinStatistic(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	Long getMixinStatisticCount(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	void createTempTable();
	
	/**
	 * 使用唯一键更新选择性记录
	 * @param record 需要更新的信息
	 * @return 影响记录数
	 */
	int updateByUniqueKeySelective(NewValidUser record);
}