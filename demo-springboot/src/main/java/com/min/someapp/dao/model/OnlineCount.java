package com.min.someapp.dao.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OnlineCount {
    private Integer serverId;

    private String platform;

    @JsonFormat(pattern="MM月dd日 HH:mm",timezone = "GMT+8")
    private Date time;

    private Integer count;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}