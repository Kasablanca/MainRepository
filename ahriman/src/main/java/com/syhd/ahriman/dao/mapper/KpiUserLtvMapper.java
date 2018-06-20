package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.KpiUserLtv;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;

public interface KpiUserLtvMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KpiUserLtv record);

    int insertSelective(KpiUserLtv record);

    KpiUserLtv selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KpiUserLtv record);

    int updateByPrimaryKey(KpiUserLtv record);

	List<KpiUserLtv> getStatistic(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	Long getStatisticCount(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	List<String> getAllPlatform();

	/**
	 * 创建存储今天用户价值LTV的临时表
	 */
	void createTodayKpiUserLtvTable();

	Long getMixinStatisticCount(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);

	List<KpiUserLtv> getMixinStatistic(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);

	Date getLastCountDate(Integer serverid);

	void batchInsert(@Param("records")List<KpiUserLtv> recordList, @Param("storedTable")String storedTable);
}