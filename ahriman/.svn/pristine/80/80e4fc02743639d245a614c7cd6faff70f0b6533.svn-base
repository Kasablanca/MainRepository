package com.syhd.ahriman.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date addTime;

    private Integer addAccId;

    private Date updTime;

    private Integer updAccId;

    private Integer verNo;

    @NotNull(message="账号不能为空")
    @Length(min=5,max=15,message="账号长度需要在5-15位之间")
    private String accName;

    @NotNull(message="用户名不能为空")
    @Length(min=2,max=15,message="用户名长度需要在2-15位之间")
    private String username;

    @NotNull(message="密码不能为空")
    @Length(min=32,max=32,message="密码无效")
    private String password;

    private Date lastLoginTime;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getAddAccId() {
        return addAccId;
    }

    public void setAddAccId(Integer addAccId) {
        this.addAccId = addAccId;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Integer getUpdAccId() {
        return updAccId;
    }

    public void setUpdAccId(Integer updAccId) {
        this.updAccId = updAccId;
    }

    public Integer getVerNo() {
        return verNo;
    }

    public void setVerNo(Integer verNo) {
        this.verNo = verNo;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName == null ? null : accName.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", addTime=" + addTime + ", addAccId=" + addAccId + ", updTime=" + updTime
				+ ", updAccId=" + updAccId + ", verNo=" + verNo + ", accName=" + accName + ", username=" + username
				+ ", password=" + password + ", lastLoginTime=" + lastLoginTime + ", status=" + status + "]";
	}
}