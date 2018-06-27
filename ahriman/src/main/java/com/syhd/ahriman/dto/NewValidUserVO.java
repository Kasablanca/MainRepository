package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NewValidUserVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date date;
	
	private Integer newUserCount; // 新增用户数
	
	private Integer disposableCount; // 一次性用户数

    private Integer commonlyCount; // 一般用户数

    private Integer loyaltyCount; // 忠实用户数
    
    private BigDecimal disposableRate; // 一次性用户占比
    
    private BigDecimal commonlyRate; // 一般用户占比
    
    private BigDecimal loyaltyRate; // 忠实用户占比
    
    public NewValidUserVO() {
    	// default
    }
    
    public NewValidUserVO(Date date) {
    	this.date = date;
    	this.commonlyCount = 0;
    	this.commonlyRate = BigDecimal.ZERO;
    	this.disposableCount = 0;
    	this.disposableRate = BigDecimal.ZERO;
    	this.loyaltyCount = 0;
    	this.loyaltyRate = BigDecimal.ZERO;
    	this.newUserCount = 0;
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNewUserCount() {
		return newUserCount;
	}

	public void setNewUserCount(Integer newUserCount) {
		this.newUserCount = newUserCount;
	}

	public Integer getDisposableCount() {
		return disposableCount;
	}

	public void setDisposableCount(Integer disposableCount) {
		this.disposableCount = disposableCount;
	}

	public Integer getCommonlyCount() {
		return commonlyCount;
	}

	public void setCommonlyCount(Integer commonlyCount) {
		this.commonlyCount = commonlyCount;
	}

	public Integer getLoyaltyCount() {
		return loyaltyCount;
	}

	public void setLoyaltyCount(Integer loyaltyCount) {
		this.loyaltyCount = loyaltyCount;
	}

	public BigDecimal getDisposableRate() {
		return disposableRate;
	}

	public void setDisposableRate(BigDecimal disposableRate) {
		this.disposableRate = disposableRate;
	}

	public BigDecimal getCommonlyRate() {
		return commonlyRate;
	}

	public void setCommonlyRate(BigDecimal commonlyRate) {
		this.commonlyRate = commonlyRate;
	}

	public BigDecimal getLoyaltyRate() {
		return loyaltyRate;
	}

	public void setLoyaltyRate(BigDecimal loyaltyRate) {
		this.loyaltyRate = loyaltyRate;
	}
	
	/**
	 * 填满日期，以0填充
	 * @param list 需要填满的列表
	 * @param start 开始日期（包含）
	 * @param end 结束日期（不包含）
	 * @return 填满后的列表
	 */
	public static List<NewValidUserVO> fill(List<NewValidUserVO> list,Date start,Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		
		Date temp;
		
		for(int i = 0; i < list.size(); i++) {
			NewValidUserVO elem = list.get(i);
			
			while((temp=cal.getTime()).before(elem.date)) {
				list.add(i++, new NewValidUserVO(temp));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(list.size()==0 && start.before(end)) {
			list.add(new NewValidUserVO(start));
		}
		
		if(list.size()>0) {
			while((temp=dateAdd(cal,list.get(list.size()-1).date)).before(end)) {
				list.add(new NewValidUserVO(temp));
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
	
}
