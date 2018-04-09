package com.min.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="data_info")
public class Data {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer dataId;

    private Integer verNo;

    private String addAcc;

    private Date addTime;

    private String updAcc;

    private Date updTime;

    private String dataName;

    private String dataDownloadUrl;

    private Integer dataVolume;

    private Byte dataType;

    private Byte dataLevel;
/*
    @Column(name="user_id",insertable=false,updatable=false)
    private String userId;*/

    private Date uploadedTime;

    private Date effectiveTime;

    private Date failureTime;

    private String remark;

    private Byte useFlag;

    private Byte publicFlag;

    private Byte encryptFlag;

    private String encryptAccount;

    private String encryptPwd;
    
    /**资料上传者*/
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    private User user;
/*    
    @ManyToMany(mappedBy="datas")
    private List<Meeting> meetings = new ArrayList<>();
    
    @OneToMany(mappedBy="data")
    private List<MeetingData> meetingDatas = new ArrayList<>();*/

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
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

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName == null ? null : dataName.trim();
    }

    public String getDataDownloadUrl() {
        return dataDownloadUrl;
    }

    public void setDataDownloadUrl(String dataDownloadUrl) {
        this.dataDownloadUrl = dataDownloadUrl == null ? null : dataDownloadUrl.trim();
    }

    public Integer getDataVolume() {
        return dataVolume;
    }

    public void setDataVolume(Integer dataVolume) {
        this.dataVolume = dataVolume;
    }

    public Byte getDataType() {
        return dataType;
    }

    public void setDataType(Byte dataType) {
        this.dataType = dataType;
    }

    public Byte getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(Byte dataLevel) {
        this.dataLevel = dataLevel;
    }
/*
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }*/

    public Date getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(Date uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Byte useFlag) {
        this.useFlag = useFlag;
    }

    public Byte getPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(Byte publicFlag) {
        this.publicFlag = publicFlag;
    }

    public Byte getEncryptFlag() {
        return encryptFlag;
    }

    public void setEncryptFlag(Byte encryptFlag) {
        this.encryptFlag = encryptFlag;
    }

    public String getEncryptAccount() {
        return encryptAccount;
    }

    public void setEncryptAccount(String encryptAccount) {
        this.encryptAccount = encryptAccount == null ? null : encryptAccount.trim();
    }

    public String getEncryptPwd() {
        return encryptPwd;
    }

    public void setEncryptPwd(String encryptPwd) {
        this.encryptPwd = encryptPwd == null ? null : encryptPwd.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
/*
	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataId == null) ? 0 : dataId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		if (dataId == null) {
			if (other.dataId != null)
				return false;
		} else if (!dataId.equals(other.dataId))
			return false;
		return true;
	}
}