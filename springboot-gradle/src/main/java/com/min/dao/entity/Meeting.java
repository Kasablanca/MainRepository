package com.min.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="meeting_info")
public class Meeting {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer meetingId;

    private Integer verNo;

    private String addAcc;

    private Date addTime;

    private String updAcc;

    private Date updTime;

    private String meetingNo;

    private String meetingName;

    private String meetingAddress;

    private Integer meetingDuration;

    //private String userId;

    private Integer expectedJoinNumber;

    private Integer actualJoinNumber;

    private Date expectedStartTime;

    private Date actualStartTime;

    private Date expireTime;

    private String resourceUrl;

    private String remark;

    private Byte status;

    private Byte roomStatus;

    private Byte useFlag;

    private Byte openFlag;
    
    /**会议发起人*/
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    private User user;
    
    /**会议资料*/
    @ManyToMany
    @JoinTable(name="meeting_data_info",joinColumns= {@JoinColumn(name="meeting_id")},
    	inverseJoinColumns= {@JoinColumn(name="data_id")})
    private List<Data> datas = new ArrayList<>();
    
    @OneToMany(mappedBy="meeting")
    private List<MeetingData> meetingDatas = new ArrayList<>();
    
    @OneToMany(mappedBy="meeting")
    private List<MeetingUser> meetingUsers = new ArrayList<>();

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
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

    public String getMeetingNo() {
        return meetingNo;
    }

    public void setMeetingNo(String meetingNo) {
        this.meetingNo = meetingNo == null ? null : meetingNo.trim();
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName == null ? null : meetingName.trim();
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress == null ? null : meetingAddress.trim();
    }

    public Integer getMeetingDuration() {
        return meetingDuration;
    }

    public void setMeetingDuration(Integer meetingDuration) {
        this.meetingDuration = meetingDuration;
    }
/*
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }*/

    public Integer getExpectedJoinNumber() {
        return expectedJoinNumber;
    }

    public void setExpectedJoinNumber(Integer expectedJoinNumber) {
        this.expectedJoinNumber = expectedJoinNumber;
    }

    public Integer getActualJoinNumber() {
        return actualJoinNumber;
    }

    public void setActualJoinNumber(Integer actualJoinNumber) {
        this.actualJoinNumber = actualJoinNumber;
    }

    public Date getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Date expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Byte roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Byte getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Byte useFlag) {
        this.useFlag = useFlag;
    }

    public Byte getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(Byte openFlag) {
        this.openFlag = openFlag;
    }

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	public List<MeetingUser> getMeetingUsers() {
		return meetingUsers;
	}

	public void setMeetingUsers(List<MeetingUser> meetingUsers) {
		this.meetingUsers = meetingUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MeetingData> getMeetingDatas() {
		return meetingDatas;
	}

	public void setMeetingDatas(List<MeetingData> meetingDatas) {
		this.meetingDatas = meetingDatas;
	}

	@Override
	public String toString() {
		return "Meeting [meetingId=" + meetingId + ", meetingName=" + meetingName + ", expectedStartTime="
				+ expectedStartTime + "]";
	}
}