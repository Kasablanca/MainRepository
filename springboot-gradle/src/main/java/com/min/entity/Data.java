package com.min.entity;

import java.util.Date;

public class Data {
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

    private String userId;

    private Date uploadedTime;

    private Date effectiveTime;

    private Date failureTime;

    private String remark;

    private Byte useFlag;

    private Byte publicFlag;

    private Byte encryptFlag;

    private String encryptAccount;

    private String encryptPwd;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

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
}