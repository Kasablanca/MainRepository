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
	 * @param start 开始日期（包含）
	 * @param end 结束日期（不包含）
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
		
		if(list.size()==0 && start.before(end)) {
			list.add(new KpiStatistic(start, 0));
		}
		
		if(list.size()>0) {
			while((temp=dateAdd(cal,list.get(list.size()-1).date)).before(end)) {
				list.add(new KpiStatistic(temp, 0));
			}
		}
		
		return list;
	}
	
	/**
	 * 将日期增加一天
	 * @param cal 日历
	 * @param target 目标日期
	 * @return 结果日期
	 */
	public static Date dateAdd(Calendar cal, Date target) {
		cal.setTime(target);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	/*
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse("2018-06-10");
		Date end = sdf.parse("2018-06-20");
		
		List<KpiStatistic> list = fill(new ArrayList<KpiStatistic>(), start, end);
		
		for(KpiStatistic item : list) {
			System.out.println(item.date);
		}
	}*/

}
