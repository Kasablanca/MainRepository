package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	
	public KpiStatistic() {
		super();
	}
	public KpiStatistic(Date date, Integer count) {
		super();
		this.date = date;
		this.count = count;
	}
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
	
	/**
	 * 填满日期，以0填充
	 * @param list 需要填满的列表
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 填满后的列表
	 */
	public static List<KpiStatistic> fill(List<KpiStatistic> list,Date start,Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		
		Date temp;
		
		for(int i = 0; i < list.size(); i++) {
			KpiStatistic elem = list.get(i);
			
			while((temp=cal.getTime()).before(elem.date)) {
				list.add(i++, new KpiStatistic(temp, 0));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		if(list.size()>0) {
			while((temp=list.get(list.size()-1).date).before(end)) {
				cal.setTime(temp);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				list.add(new KpiStatistic(cal.getTime(), 0));
			}
		}
		
		return list;
	}

}
