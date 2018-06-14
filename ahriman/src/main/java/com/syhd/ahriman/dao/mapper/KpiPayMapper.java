package com.syhd.ahriman.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.KpiPay;
import com.syhd.ahriman.dto.KpiPayVO;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;

public interface KpiPayMapper {
    int deleteByPrimaryKey(Long id);

	int insert(KpiPay record);

	int insertSelective(KpiPay record);

	KpiPay selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(KpiPay record);
    
    List<KpiPayVO> getStatistic(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

    Long getStatisticCount(@Param("param")RequestPayload copy, @Param("pageAndSort")PageAndSort pageAndSort);

	List<String> getAllPlatform();

	Date getLastCountDate(Integer serverid);

	void batchInsert(@Param("records")List<KpiPay> recordList);

	/**
	 * 创建存储今天支付数据的临时表
	 */
	void createTodayKpiPayTable();
	
	/**
	 * 插入临时表
	 * @param recordList 待插入数
	 */
	void batchInsertTemp(@Param("records")List<KpiPay> recordList);

	List<KpiPayVO> getMixinStatistic(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);

	Long getMixinStatisticCount(@Param("param")RequestPayload param, @Param("pageAndSort")PageAndSort pageAndSort);
}