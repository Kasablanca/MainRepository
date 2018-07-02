package com.min.someapp.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlayerRemain implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    private Integer serverId;

    private String platform;
    
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    private BigDecimal day1;

    private BigDecimal day2;

    private BigDecimal day3;

    private BigDecimal day4;

    private BigDecimal day5;

    private BigDecimal day6;

    private BigDecimal day7;

    private BigDecimal day14;

    private BigDecimal day30;

    public PlayerRemain() {
		super();
	}

	public PlayerRemain(Date date) {
		super();
		this.date = date;
		this.day1 = BigDecimal.ZERO;
		this.day2 = BigDecimal.ZERO;
		this.day3 = BigDecimal.ZERO;
		this.day4 = BigDecimal.ZERO;
		this.day5 = BigDecimal.ZERO;
		this.day6 = BigDecimal.ZERO;
		this.day7 = BigDecimal.ZERO;
		this.day14 = BigDecimal.ZERO;
		this.day30 = BigDecimal.ZERO;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getDay1() {
        return day1;
    }

    public void setDay1(BigDecimal day1) {
        this.day1 = day1;
    }

    public BigDecimal getDay2() {
        return day2;
    }

    public void setDay2(BigDecimal day2) {
        this.day2 = day2;
    }

    public BigDecimal getDay3() {
        return day3;
    }

    public void setDay3(BigDecimal day3) {
        this.day3 = day3;
    }

    public BigDecimal getDay4() {
        return day4;
    }

    public void setDay4(BigDecimal day4) {
        this.day4 = day4;
    }

    public BigDecimal getDay5() {
        return day5;
    }

    public void setDay5(BigDecimal day5) {
        this.day5 = day5;
    }

    public BigDecimal getDay6() {
        return day6;
    }

    public void setDay6(BigDecimal day6) {
        this.day6 = day6;
    }

    public BigDecimal getDay7() {
        return day7;
    }

    public void setDay7(BigDecimal day7) {
        this.day7 = day7;
    }

    public BigDecimal getDay14() {
        return day14;
    }

    public void setDay14(BigDecimal day14) {
        this.day14 = day14;
    }

    public BigDecimal getDay30() {
        return day30;
    }

    public void setDay30(BigDecimal day30) {
        this.day30 = day30;
    }
    
    /**
	 * 填满日期，以0填充
	 * @param list 需要填满的列表
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 填满后的列表
	 */
	public static List<PlayerRemain> fill(List<PlayerRemain> list,Date start,Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		
		Date temp;
		
		for(int i = 0; i < list.size(); i++) {
			PlayerRemain elem = list.get(i);
			
			while((temp=cal.getTime()).before(elem.date)) {
				list.add(i++, new PlayerRemain(temp));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(list.size()==0 && start.before(end)) {
			list.add(new PlayerRemain(start));
		}
		
		if(list.size()>0) {
			while((temp=dateAdd(cal,list.get(list.size()-1).date)).before(end)) {
				list.add(new PlayerRemain(temp));
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