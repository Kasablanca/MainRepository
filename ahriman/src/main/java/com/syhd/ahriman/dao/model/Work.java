package com.syhd.ahriman.dao.model;

import java.util.Date;

public class Work {
    private Integer id;

    private Date birthday;

    private Date addTime;

    private Date closingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

	@Override
	public String toString() {
		return "Work [id=" + id + ", birthday=" + birthday + ", addTime=" + addTime + ", closingTime=" + closingTime
				+ "]";
	}
}