package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 代表通用的KPI指标
 * @author MIN.LEE
 *
 */
public class KpiStatistic implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="MM月dd日",timezone = "GMT+8")
	private Date date;
	
	private Integer count;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
