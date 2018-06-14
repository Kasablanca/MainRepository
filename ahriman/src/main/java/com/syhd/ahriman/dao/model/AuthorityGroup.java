package com.syhd.ahriman.dao.model;

import java.util.Date;

public class AuthorityGroup {
    private Integer id;

    private Integer addAccId;

    private Date addTime;

    private Integer updAccId;

    private Date updTime;

    private Integer verNo;

    private String groupName;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddAccId() {
        return addAccId;
    }

    public void setAddAccId(Integer addAccId) {
        this.addAccId = addAccId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdAccId() {
        return updAccId;
    }

    public void setUpdAccId(Integer updAccId) {
        this.updAccId = updAccId;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Integer getVerNo() {
        return verNo;
    }

    public void setVerNo(Integer verNo) {
        this.verNo = verNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "AuthorityGroup [id=" + id + ", addAccId=" + addAccId + ", addTime=" + addTime + ", updAccId=" + updAccId
				+ ", updTime=" + updTime + ", verNo=" + verNo + ", groupName=" + groupName + ", status=" + status + "]";
	}
}