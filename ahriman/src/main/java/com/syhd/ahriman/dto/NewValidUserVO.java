package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
	
}
