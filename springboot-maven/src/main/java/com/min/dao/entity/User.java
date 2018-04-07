package com.min.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_info")
public class User {
	
	@Id
    private String userId;

    private Integer verNo;

    private String addAcc;

    private Date addTime;

    private String updAcc;

    private Date updTime;

    private String account;

    private String userNick;

    private Byte userSex;

    private Integer userAge;

    private String userAvatarUrl;

    private String linkPhone;

    private String linkMail;

    private String linkQq;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick == null ? null : userNick.trim();
    }

    public Byte getUserSex() {
        return userSex;
    }

    public void setUserSex(Byte userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl == null ? null : userAvatarUrl.trim();
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone == null ? null : linkPhone.trim();
    }

    public String getLinkMail() {
        return linkMail;
    }

    public void setLinkMail(String linkMail) {
        this.linkMail = linkMail == null ? null : linkMail.trim();
    }

    public String getLinkQq() {
        return linkQq;
    }

    public void setLinkQq(String linkQq) {
        this.linkQq = linkQq == null ? null : linkQq.trim();
    }
    
    public static interface UserProjection {
    	String getUserId();
    	String getUserNick();
    	Integer getUserAge();
    }
    
}