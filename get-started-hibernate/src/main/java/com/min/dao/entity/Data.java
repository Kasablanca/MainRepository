package com.min.dao.entity;

public class Data {

    private Integer dataId;

    private Integer verNo;

    private String dataName;

    private String dataDownloadUrl;
    
    private Byte status;
    
    private User user;

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

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataDownloadUrl() {
		return dataDownloadUrl;
	}

	public void setDataDownloadUrl(String dataDownloadUrl) {
		this.dataDownloadUrl = dataDownloadUrl;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}