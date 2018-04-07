package com.min.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="meeting_data_info")
public class MeetingData {
	
	@Id
    private String meetingDataId;

    private Integer verNo;

    private String addAcc;

    private Date addTime;

    private String updAcc;

    private Date updTime;

    @Column(name="user_id",insertable=false,updatable=false)
    private Integer meetingId;

    @Column(name="user_id",insertable=false,updatable=false)
    private Integer dataId;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="data_id")
    private Data data;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="meeting_id")
    private Meeting meeting;

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

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
}