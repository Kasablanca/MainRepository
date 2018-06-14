package com.syhd.ahriman.dao.model;

import java.util.Date;

public class UserRegistered {
    private Date date;

    private String platform;

    private Integer dailyRegistered;

    private Integer totalRegistered;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public Integer getDailyRegistered() {
        return dailyRegistered;
    }

    public void setDailyRegistered(Integer dailyRegistered) {
        this.dailyRegistered = dailyRegistered;
    }

    public Integer getTotalRegistered() {
        return totalRegistered;
    }

    public void setTotalRegistered(Integer totalRegistered) {
        this.totalRegistered = totalRegistered;
    }
}