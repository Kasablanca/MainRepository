package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GeneralOnlineInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date date;

    private BigDecimal totalDuration; //平均在线

    private Integer maxOnline; // 最高在线

    private Integer liveness; // 活跃用户

    private Integer loginCount; // 日登陆次数

    private BigDecimal loginCountAvg; // 平均登陆次数
    
    private Integer newPlayerCount; // 日新增角色数
    
    private Integer newUserCount; // 日新增账户数

    private Integer backUserCount; // 回归用户

    private BigDecimal onlineTimeAvg; // 平均在线时长
    
    private Integer totalDurationNew; // 新用户在线时长
    
    private Integer totalDurationOld; // 老用户在线时长

	public GeneralOnlineInfoVO() {
		super();
	}

	public GeneralOnlineInfoVO(Date date) {
		this.date = date;
		this.backUserCount = 0;
		this.liveness = 0;
		this.loginCount = 0;
		this.loginCountAvg = BigDecimal.ZERO;
		this.maxOnline = 0;
		this.newPlayerCount = 0;
		this.newUserCount = 0;
		this.onlineTimeAvg = BigDecimal.ZERO;
		this.totalDuration = BigDecimal.ZERO;
		this.totalDurationNew = 0;
		this.totalDurationOld = 0;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(BigDecimal totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Integer getMaxOnline() {
		return maxOnline;
	}

	public void setMaxOnline(Integer maxOnline) {
		this.maxOnline = maxOnline;
	}

	public Integer getLiveness() {
		return liveness;
	}

	public void setLiveness(Integer liveness) {
		this.liveness = liveness;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public BigDecimal getLoginCountAvg() {
		return loginCountAvg;
	}

	public void setLoginCountAvg(BigDecimal loginCountAvg) {
		this.loginCountAvg = loginCountAvg;
	}

	public Integer getNewPlayerCount() {
		return newPlayerCount;
	}

	public void setNewPlayerCount(Integer newPlayerCount) {
		this.newPlayerCount = newPlayerCount;
	}

	public Integer getNewUserCount() {
		return newUserCount;
	}

	public void setNewUserCount(Integer newUserCount) {
		this.newUserCount = newUserCount;
	}

	public Integer getBackUserCount() {
		return backUserCount;
	}

	public void setBackUserCount(Integer backUserCount) {
		this.backUserCount = backUserCount;
	}

	public BigDecimal getOnlineTimeAvg() {
		return onlineTimeAvg;
	}

	public void setOnlineTimeAvg(BigDecimal onlineTimeAvg) {
		this.onlineTimeAvg = onlineTimeAvg;
	}

	public Integer getTotalDurationNew() {
		return totalDurationNew;
	}

	public void setTotalDurationNew(Integer totalDurationNew) {
		this.totalDurationNew = totalDurationNew;
	}

	public Integer getTotalDurationOld() {
		return totalDurationOld;
	}

	public void setTotalDurationOld(Integer totalDurationOld) {
		this.totalDurationOld = totalDurationOld;
	}
	
	/**
	 * 填满日期，以0填充
	 * @param list 需要填满的列表
	 * @param start 开始日期（包含）
	 * @param end 结束日期（不包含）
	 * @return 填满后的列表
	 */
	public static List<GeneralOnlineInfoVO> fill(List<GeneralOnlineInfoVO> list,Date start,Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		
		Date temp;
		
		for(int i = 0; i < list.size(); i++) {
			GeneralOnlineInfoVO elem = list.get(i);
			
			while((temp=cal.getTime()).before(elem.date)) {
				list.add(i++, new GeneralOnlineInfoVO(temp));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(list.size()==0 && start.before(end)) {
			list.add(new GeneralOnlineInfoVO(start));
		}
		
		if(list.size()>0) {
			while((temp=dateAdd(cal,list.get(list.size()-1).date)).before(end)) {
				list.add(new GeneralOnlineInfoVO(temp));
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
