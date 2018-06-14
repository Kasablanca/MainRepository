package com.syhd.ahriman.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class KpiPay {
    private Long id;

	private Date date;

	private Integer serverId;

	private String platform;

	private Integer activeUserNumber;

	private BigDecimal revenue;

	private Integer payUserNumber;

	private Integer oldPlayerNumber;

	private BigDecimal oldPayRevenue;

	private Integer oldPayNumber;

	private Integer newPlayerNumber;

	private BigDecimal newPayRevenue;

	private Integer newPayNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public Integer getActiveUserNumber() {
		return activeUserNumber;
	}

	public void setActiveUserNumber(Integer activeUserNumber) {
		this.activeUserNumber = activeUserNumber;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public Integer getPayUserNumber() {
		return payUserNumber;
	}

	public void setPayUserNumber(Integer payUserNumber) {
		this.payUserNumber = payUserNumber;
	}

	public Integer getOldPlayerNumber() {
		return oldPlayerNumber;
	}

	public void setOldPlayerNumber(Integer oldPlayerNumber) {
		this.oldPlayerNumber = oldPlayerNumber;
	}

	public BigDecimal getOldPayRevenue() {
		return oldPayRevenue;
	}

	public void setOldPayRevenue(BigDecimal oldPayRevenue) {
		this.oldPayRevenue = oldPayRevenue;
	}

	public Integer getOldPayNumber() {
		return oldPayNumber;
	}

	public void setOldPayNumber(Integer oldPayNumber) {
		this.oldPayNumber = oldPayNumber;
	}

	public Integer getNewPlayerNumber() {
		return newPlayerNumber;
	}

	public void setNewPlayerNumber(Integer newPlayerNumber) {
		this.newPlayerNumber = newPlayerNumber;
	}

	public BigDecimal getNewPayRevenue() {
		return newPayRevenue;
	}

	public void setNewPayRevenue(BigDecimal newPayRevenue) {
		this.newPayRevenue = newPayRevenue;
	}

	public Integer getNewPayNumber() {
		return newPayNumber;
	}

	public void setNewPayNumber(Integer newPayNumber) {
		this.newPayNumber = newPayNumber;
	}
}