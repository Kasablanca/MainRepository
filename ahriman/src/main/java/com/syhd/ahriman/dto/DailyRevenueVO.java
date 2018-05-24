package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DailyRevenueVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="MM月dd日",timezone = "GMT+8")
	private Date date;
	
	private Integer money;
	private Integer moneyType;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(Integer moneyType) {
		this.moneyType = moneyType;
	}
	
}
