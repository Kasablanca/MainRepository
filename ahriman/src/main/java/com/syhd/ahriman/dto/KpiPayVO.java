package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class KpiPayVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date date;

    private Integer activeUserNumber;//活跃用户

    private BigDecimal revenue;//每日收入

    private Integer payUserNumber;//付费用户数

    private BigDecimal payArppu;//付费ARPPU

    private BigDecimal payRate;//付费率

    private BigDecimal activeArpu;//活跃ARPU

    private Integer oldPlayerNumber;//老玩家数量

    private BigDecimal oldPayRevenue;//老玩家付费额

    private Integer oldPayNumber;//老玩家付费人数

    private BigDecimal payArppuOld;//老玩家ARPPU

    private BigDecimal payRateOld;//老玩家付费率

    private BigDecimal activeArpuOld;//老玩家ARPU

    private Integer newPlayerNumber;//新增角色数

    private BigDecimal newPayRevenue;//新增付费额

    private Integer newPayNumber;//新增付费人数

    private BigDecimal payArppuNew;//新增ARPPU

    private BigDecimal payRateNew;//首日付费转化率

    private BigDecimal activeArpuNew;//新增ARPU

    public KpiPayVO() {
		super();
	}

	public KpiPayVO(Date date) {
		super();
		this.date = date;
		this.activeArpu = BigDecimal.ZERO;
		this.activeArpuNew = BigDecimal.ZERO;
		this.activeArpuOld = BigDecimal.ZERO;
		this.activeUserNumber = 0;
		this.newPayNumber = 0;
		this.newPayRevenue = BigDecimal.ZERO;
		this.newPlayerNumber = 0;
		this.oldPayNumber = 0;
		this.oldPayRevenue = BigDecimal.ZERO;
		this.oldPlayerNumber = 0;
		this.payArppu = BigDecimal.ZERO;
		this.payArppuNew = BigDecimal.ZERO;
		this.payArppuOld = BigDecimal.ZERO;
		this.payRate = BigDecimal.ZERO;
		this.payRateNew = BigDecimal.ZERO;
		this.payRateOld = BigDecimal.ZERO;
		this.payUserNumber = 0;
	}

	public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public BigDecimal getPayArppu() {
        return payArppu;
    }

    public void setPayArppu(BigDecimal payArppu) {
        this.payArppu = payArppu;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public BigDecimal getActiveArpu() {
        return activeArpu;
    }

    public void setActiveArpu(BigDecimal activeArpu) {
        this.activeArpu = activeArpu;
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

    public BigDecimal getPayArppuOld() {
        return payArppuOld;
    }

    public void setPayArppuOld(BigDecimal payArppuOld) {
        this.payArppuOld = payArppuOld;
    }

    public BigDecimal getPayRateOld() {
        return payRateOld;
    }

    public void setPayRateOld(BigDecimal payRateOld) {
        this.payRateOld = payRateOld;
    }

    public BigDecimal getActiveArpuOld() {
        return activeArpuOld;
    }

    public void setActiveArpuOld(BigDecimal activeArpuOld) {
        this.activeArpuOld = activeArpuOld;
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

    public BigDecimal getPayArppuNew() {
        return payArppuNew;
    }

    public void setPayArppuNew(BigDecimal payArppuNew) {
        this.payArppuNew = payArppuNew;
    }

    public BigDecimal getPayRateNew() {
        return payRateNew;
    }

    public void setPayRateNew(BigDecimal payRateNew) {
        this.payRateNew = payRateNew;
    }

    public BigDecimal getActiveArpuNew() {
        return activeArpuNew;
    }

    public void setActiveArpuNew(BigDecimal activeArpuNew) {
        this.activeArpuNew = activeArpuNew;
    }
    
    /**
	 * 填满日期，以0填充
	 * @param list 需要填满的列表
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 填满后的列表
	 */
	public static List<KpiPayVO> fill(List<KpiPayVO> list,Date start,Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		
		Date temp;
		
		for(int i = 0; i < list.size(); i++) {
			KpiPayVO elem = list.get(i);
			
			while((temp=cal.getTime()).before(elem.date)) {
				list.add(i++, new KpiPayVO(temp));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		while((temp=list.get(list.size()-1).date).before(end)) {
			cal.setTime(temp);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			list.add(new KpiPayVO(cal.getTime()));
		}
		
		return list;
	}
}