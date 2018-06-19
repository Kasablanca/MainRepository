package com.min.springbootdemo.dao.model;

import java.util.Date;

public class Authority {
    private Integer id;

    private Integer parentId;

    private String name;

    private String path;

    private Byte type;

    private Byte status;

    private Integer verNo;

    private Date addTime;

    private Integer addAccId;

    private Date updTime;

    private Integer updAccId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getVerNo() {
        return verNo;
    }

    public void setVerNo(Integer verNo) {
        this.verNo = verNo;
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
}