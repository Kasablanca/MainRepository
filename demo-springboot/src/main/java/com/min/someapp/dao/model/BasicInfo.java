package com.min.someapp.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class BasicInfo {
    private Long id;

    private Date date;

    private Integer serverId;

    private String platform;

    private BigDecimal totalRevenue;

    private BigDecimal dailyRevenue;

    private BigDecimal dailyRevenueNew;

    private Integer liveUser;

    private Integer liveUserNew;

    private Integer retentionDay2;

    private Integer retentionDay3;

    private Integer retentionDay5;

    private Integer retentionDay7;

    private Integer retentionDay15;

    private Integer retentionDay30;

    private Integer payUser;

    private Integer payUserNew;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}