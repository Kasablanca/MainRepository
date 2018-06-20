package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
	
}
