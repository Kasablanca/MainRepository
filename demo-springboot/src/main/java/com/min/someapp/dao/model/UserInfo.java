package com.min.someapp.dao.model;

public class UserInfo {
    private Integer id;

    private String uid;

    private String account;

    private String encryptedpwd;

    private String imsi;

    private String security;

    private Integer lastloginserverid;

    private Integer loginingserverid;

    private String regtype;

    private String regip;

    private String regtime;

    private String lastloginip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getEncryptedpwd() {
        return encryptedpwd;
    }

    public void setEncryptedpwd(String encryptedpwd) {
        this.encryptedpwd = encryptedpwd == null ? null : encryptedpwd.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security == null ? null : security.trim();
    }

    public Integer getLastloginserverid() {
        return lastloginserverid;
    }

    public void setLastloginserverid(Integer lastloginserverid) {
        this.lastloginserverid = lastloginserverid;
    }

    public Integer getLoginingserverid() {
        return loginingserverid;
    }

    public void setLoginingserverid(Integer loginingserverid) {
        this.loginingserverid = loginingserverid;
    }

    public String getRegtype() {
        return regtype;
    }

    public void setRegtype(String regtype) {
        this.regtype = regtype == null ? null : regtype.trim();
    }

    public String getRegip() {
        return regip;
    }

    public void setRegip(String regip) {
        this.regip = regip == null ? null : regip.trim();
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime == null ? null : regtime.trim();
    }

    public String getLastloginip() {
        return lastloginip;
    }

    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip == null ? null : lastloginip.trim();
    }
}