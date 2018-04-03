package com.min.entity;

import java.util.Date;

public class MeetingData {
    private String meetingDataId;

    private Integer verNo;

    private String addAcc;

    private Date addTime;

    private String updAcc;

    private Date updTime;

    private Integer meetingId;

    private Integer dataId;

    public String getMeetingDataId() {
        return meetingDataId;
    }

    public void setMeetingDataId(String meetingDataId) {
        this.meetingDataId = meetingDataId == null ? null : meetingDataId.trim();
    }

    public Integer getVerNo() {
        return verNo;
    }

    public void setVerNo(Integer verNo) {
        this.verNo = verNo;
    }

    public String getAddAcc() {
        return addAcc;
    }

    public void setAddAcc(String addAcc) {
        this.addAcc = addAcc == null ? null : addAcc.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getUpdAcc() {
        return updAcc;
    }

    public void setUpdAcc(String updAcc) {
        this.updAcc = updAcc == null ? null : updAcc.trim();
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }
}