package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BasicInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date date;
	
	private Integer totalRegistered; //总注册

	private BigDecimal totalRevenue; //总收入

    private BigDecimal dailyRevenue; //日收入

    private BigDecimal dailyRevenueNew; //新玩家收入
    
    private BigDecimal dailyRevenueOld; //老玩家收入

    private Integer liveUser; //活跃用户数

    private Integer liveUserNew; //新增用户
    
    private Integer liveUserOld; //老玩家活跃数

    private Integer retentionDay2; //次日留存

    private Integer retentionDay3; //3日留存

    private Integer retentionDay5; //5日留存

    private Integer retentionDay7; //7日留存

    private Integer retentionDay15; //15日留存

    private Integer retentionDay30; //30留存

    private Integer payUser; //充值用户数

    private Integer payUserNew; //新用户充值人数
    
    private Integer payUserOld; //老用户充值人数
	
    private BigDecimal payRate; //付费率
    
    private BigDecimal payRateNew; //新用户付费率
    
    private BigDecimal payRateOld; //老用户付费率
    
    private BigDecimal arpu; //ARPU
    
    private BigDecimal arppu; //ARPPU

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTotalRegistered() {
		return totalRegistered;
	}

	public void setTotalRegistered(Integer totalRegistered) {
		this.totalRegistered = totalRegistered;
	}

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public BigDecimal getDailyRevenue() {
		return dailyRevenue;
	}

	public void setDailyRevenue(BigDecimal dailyRevenue) {
		this.dailyRevenue = dailyRevenue;
	}

	public BigDecimal getDailyRevenueNew() {
		return dailyRevenueNew;
	}

	public void setDailyRevenueNew(BigDecimal dailyRevenueNew) {
		this.dailyRevenueNew = dailyRevenueNew;
	}

	public BigDecimal getDailyRevenueOld() {
		return dailyRevenueOld;
	}

	public void setDailyRevenueOld(BigDecimal dailyRevenueOld) {
		this.dailyRevenueOld = dailyRevenueOld;
	}

	public Integer getLiveUser() {
		return liveUser;
	}

	public void setLiveUser(Integer liveUser) {
		this.liveUser = liveUser;
	}

	public Integer getLiveUserNew() {
		return liveUserNew;
	}

	public void setLiveUserNew(Integer liveUserNew) {
		this.liveUserNew = liveUserNew;
	}

	public Integer getLiveUserOld() {
		return liveUserOld;
	}

	public void setLiveUserOld(Integer liveUserOld) {
		this.liveUserOld = liveUserOld;
	}

	public Integer getRetentionDay2() {
		return retentionDay2;
	}

	public void setRetentionDay2(Integer retentionDay2) {
		this.retentionDay2 = retentionDay2;
	}

	public Integer getRetentionDay3() {
		return retentionDay3;
	}

	public void setRetentionDay3(Integer retentionDay3) {
		this.retentionDay3 = retentionDay3;
	}

	public Integer getRetentionDay5() {
		return retentionDay5;
	}

	public void setRetentionDay5(Integer retentionDay5) {
		this.retentionDay5 = retentionDay5;
	}

	public Integer getRetentionDay7() {
		return retentionDay7;
	}

	public void setRetentionDay7(Integer retentionDay7) {
		this.retentionDay7 = retentionDay7;
	}

	public Integer getRetentionDay15() {
		return retentionDay15;
	}

	public void setRetentionDay15(Integer retentionDay15) {
		this.retentionDay15 = retentionDay15;
	}

	public Integer getRetentionDay30() {
		return retentionDay30;
	}

	public void setRetentionDay30(Integer retentionDay30) {
		this.retentionDay30 = retentionDay30;
	}

	public Integer getPayUser() {
		return payUser;
	}

	public void setPayUser(Integer payUser) {
		this.payUser = payUser;
	}

	public Integer getPayUserNew() {
		return payUserNew;
	}

	public void setPayUserNew(Integer payUserNew) {
		this.payUserNew = payUserNew;
	}

	public Integer getPayUserOld() {
		return payUserOld;
	}

	public void setPayUserOld(Integer payUserOld) {
		this.payUserOld = payUserOld;
	}

	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}

	public BigDecimal getPayRateNew() {
		return payRateNew;
	}

	public void setPayRateNew(BigDecimal payRateNew) {
		this.payRateNew = payRateNew;
	}

	public BigDecimal getPayRateOld() {
		return payRateOld;
	}

	public void setPayRateOld(BigDecimal payRateOld) {
		this.payRateOld = payRateOld;
	}

	public BigDecimal getArpu() {
		return arpu;
	}

	public void setArpu(BigDecimal arpu) {
		this.arpu = arpu;
	}

	public BigDecimal getArppu() {
		return arppu;
	}

	public void setArppu(BigDecimal arppu) {
		this.arppu = arppu;
	}
}
