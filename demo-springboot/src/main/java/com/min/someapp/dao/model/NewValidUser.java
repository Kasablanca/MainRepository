package com.min.someapp.dao.model;

import java.util.Date;

public class NewValidUser {
    private Integer id;

    private Date date;

    private Integer serverId;

    private String platform;

    private Integer newUserCount;

    private Integer disposableCount;

    private Integer commonlyCount;

    private Integer loyaltyCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}