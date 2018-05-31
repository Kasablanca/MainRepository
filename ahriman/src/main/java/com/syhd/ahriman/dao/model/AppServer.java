package com.syhd.ahriman.dao.model;

import java.io.Serializable;
import java.util.Date;

public class AppServer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer serverid;

    private String servername;

    private String serverip;

    private Integer serverport;

    private String rmiurl;

    private Byte status;

    private Byte recomm;

    private Byte wlmodel;

    private Byte hefu;

    private Byte groupId;

    private String logDb;

    private Date openTime;

    public Integer getServerid() {
        return serverid;
    }

    public void setServerid(Integer serverid) {
        this.serverid = serverid;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername == null ? null : servername.trim();
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip == null ? null : serverip.trim();
    }

    public Integer getServerport() {
        return serverport;
    }

    public void setServerport(Integer serverport) {
        this.serverport = serverport;
    }

    public String getRmiurl() {
        return rmiurl;
    }

    public void setRmiurl(String rmiurl) {
        this.rmiurl = rmiurl == null ? null : rmiurl.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getRecomm() {
        return recomm;
    }

    public void setRecomm(Byte recomm) {
        this.recomm = recomm;
    }

    public Byte getWlmodel() {
        return wlmodel;
    }

    public void setWlmodel(Byte wlmodel) {
        this.wlmodel = wlmodel;
    }

    public Byte getHefu() {
        return hefu;
    }

    public void setHefu(Byte hefu) {
        this.hefu = hefu;
    }

    public Byte getGroupId() {
        return groupId;
    }

    public void setGroupId(Byte groupId) {
        this.groupId = groupId;
    }

    public String getLogDb() {
        return logDb;
    }

    public void setLogDb(String logDb) {
        this.logDb = logDb == null ? null : logDb.trim();
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }
}