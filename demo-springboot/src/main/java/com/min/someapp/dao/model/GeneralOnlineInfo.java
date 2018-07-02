package com.min.someapp.dao.model;

import java.util.Date;

public class GeneralOnlineInfo {
    private Integer id;

    private Date date;

    private Integer serverId;

    private String platform;

    private Long totalDuration;

    private Integer maxOnline;

    private Integer liveness;

    private Integer loginCount;

    private Integer newPlayerCount;

    private Integer backUserCount;

    private Integer totalDurationNew;

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

    public Long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Long totalDuration) {
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

    public Integer getNewPlayerCount() {
        return newPlayerCount;
    }

    public void setNewPlayerCount(Integer newPlayerCount) {
        this.newPlayerCount = newPlayerCount;
    }

    public Integer getBackUserCount() {
        return backUserCount;
    }

    public void setBackUserCount(Integer backUserCount) {
        this.backUserCount = backUserCount;
    }

    public Integer getTotalDurationNew() {
        return totalDurationNew;
    }

    public void setTotalDurationNew(Integer totalDurationNew) {
        this.totalDurationNew = totalDurationNew;
    }
}