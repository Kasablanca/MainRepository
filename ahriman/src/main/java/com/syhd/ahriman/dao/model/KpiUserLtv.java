package com.syhd.ahriman.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class KpiUserLtv implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    private Integer serverId;

    private String platform;

    private Integer newUserNumber;

    private BigDecimal totalMoney;

    private BigDecimal day1;

    private BigDecimal day2;

    private BigDecimal day3;

    private BigDecimal day4;

    private BigDecimal day5;

    private BigDecimal day6;

    private BigDecimal day7;

    private BigDecimal day8;

    private BigDecimal day9;

    private BigDecimal day10;

    private BigDecimal day11;

    private BigDecimal day12;

    private BigDecimal day13;

    private BigDecimal day14;

    private BigDecimal day15;

    private BigDecimal day16;

    private BigDecimal day17;

    private BigDecimal day18;

    private BigDecimal day19;

    private BigDecimal day20;

    private BigDecimal day21;

    private BigDecimal day22;

    private BigDecimal day23;

    private BigDecimal day24;

    private BigDecimal day25;

    private BigDecimal day26;

    private BigDecimal day27;

    private BigDecimal day28;

    private BigDecimal day29;

    private BigDecimal day30;
    
    public KpiUserLtv() {
    	//default
    }

    public KpiUserLtv(Date date) {
    	this.date = date;
    	this.day1 = BigDecimal.ZERO;
    	this.day2 = BigDecimal.ZERO;
    	this.day3 = BigDecimal.ZERO;
    	this.day4 = BigDecimal.ZERO;
    	this.day5 = BigDecimal.ZERO;
    	this.day6 = BigDecimal.ZERO;
    	this.day7 = BigDecimal.ZERO;
    	this.day8 = BigDecimal.ZERO;
    	this.day9 = BigDecimal.ZERO;
    	this.day10 = BigDecimal.ZERO;
    	this.day11 = BigDecimal.ZERO;
    	this.day12 = BigDecimal.ZERO;
    	this.day13 = BigDecimal.ZERO;
    	this.day14 = BigDecimal.ZERO;
    	this.day15 = BigDecimal.ZERO;
    	this.day16 = BigDecimal.ZERO;
    	this.day17 = BigDecimal.ZERO;
    	this.day18 = BigDecimal.ZERO;
    	this.day19 = BigDecimal.ZERO;
    	this.day20 = BigDecimal.ZERO;
    	this.day20 = BigDecimal.ZERO;
    	this.day21 = BigDecimal.ZERO;
    	this.day22 = BigDecimal.ZERO;
    	this.day23 = BigDecimal.ZERO;
    	this.day24 = BigDecimal.ZERO;
    	this.day25 = BigDecimal.ZERO;
    	this.day26 = BigDecimal.ZERO;
    	this.day27 = BigDecimal.ZERO;
    	this.day28 = BigDecimal.ZERO;
    	this.day29 = BigDecimal.ZERO;
    	this.day30 = BigDecimal.ZERO;
    	this.newUserNumber = 0;
    	this.totalMoney = BigDecimal.ZERO;
    }
    
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

    public Integer getNewUserNumber() {
        return newUserNumber;
    }

    public void setNewUserNumber(Integer newUserNumber) {
        this.newUserNumber = newUserNumber;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getDay1() {
        return day1;
    }

    public void setDay1(BigDecimal day1) {
        this.day1 = day1;
    }

    public BigDecimal getDay2() {
        return day2;
    }

    public void setDay2(BigDecimal day2) {
        this.day2 = day2;
    }

    public BigDecimal getDay3() {
        return day3;
    }

    public void setDay3(BigDecimal day3) {
        this.day3 = day3;
    }

    public BigDecimal getDay4() {
        return day4;
    }

    public void setDay4(BigDecimal day4) {
        this.day4 = day4;
    }

    public BigDecimal getDay5() {
        return day5;
    }

    public void setDay5(BigDecimal day5) {
        this.day5 = day5;
    }

    public BigDecimal getDay6() {
        return day6;
    }

    public void setDay6(BigDecimal day6) {
        this.day6 = day6;
    }

    public BigDecimal getDay7() {
        return day7;
    }

    public void setDay7(BigDecimal day7) {
        this.day7 = day7;
    }

    public BigDecimal getDay8() {
        return day8;
    }

    public void setDay8(BigDecimal day8) {
        this.day8 = day8;
    }

    public BigDecimal getDay9() {
        return day9;
    }

    public void setDay9(BigDecimal day9) {
        this.day9 = day9;
    }

    public BigDecimal getDay10() {
        return day10;
    }

    public void setDay10(BigDecimal day10) {
        this.day10 = day10;
    }

    public BigDecimal getDay11() {
        return day11;
    }

    public void setDay11(BigDecimal day11) {
        this.day11 = day11;
    }

    public BigDecimal getDay12() {
        return day12;
    }

    public void setDay12(BigDecimal day12) {
        this.day12 = day12;
    }

    public BigDecimal getDay13() {
        return day13;
    }

    public void setDay13(BigDecimal day13) {
        this.day13 = day13;
    }

    public BigDecimal getDay14() {
        return day14;
    }

    public void setDay14(BigDecimal day14) {
        this.day14 = day14;
    }

    public BigDecimal getDay15() {
        return day15;
    }

    public void setDay15(BigDecimal day15) {
        this.day15 = day15;
    }

    public BigDecimal getDay16() {
        return day16;
    }

    public void setDay16(BigDecimal day16) {
        this.day16 = day16;
    }

    public BigDecimal getDay17() {
        return day17;
    }

    public void setDay17(BigDecimal day17) {
        this.day17 = day17;
    }

    public BigDecimal getDay18() {
        return day18;
    }

    public void setDay18(BigDecimal day18) {
        this.day18 = day18;
    }

    public BigDecimal getDay19() {
        return day19;
    }

    public void setDay19(BigDecimal day19) {
        this.day19 = day19;
    }

    public BigDecimal getDay20() {
        return day20;
    }

    public void setDay20(BigDecimal day20) {
        this.day20 = day20;
    }

    public BigDecimal getDay21() {
        return day21;
    }

    public void setDay21(BigDecimal day21) {
        this.day21 = day21;
    }

    public BigDecimal getDay22() {
        return day22;
    }

    public void setDay22(BigDecimal day22) {
        this.day22 = day22;
    }

    public BigDecimal getDay23() {
        return day23;
    }

    public void setDay23(BigDecimal day23) {
        this.day23 = day23;
    }

    public BigDecimal getDay24() {
        return day24;
    }

    public void setDay24(BigDecimal day24) {
        this.day24 = day24;
    }

    public BigDecimal getDay25() {
        return day25;
    }

    public void setDay25(BigDecimal day25) {
        this.day25 = day25;
    }

    public BigDecimal getDay26() {
        return day26;
    }

    public void setDay26(BigDecimal day26) {
        this.day26 = day26;
    }

    public BigDecimal getDay27() {
        return day27;
    }

    public void setDay27(BigDecimal day27) {
        this.day27 = day27;
    }

    public BigDecimal getDay28() {
        return day28;
    }

    public void setDay28(BigDecimal day28) {
        this.day28 = day28;
    }

    public BigDecimal getDay29() {
        return day29;
    }

    public void setDay29(BigDecimal day29) {
        this.day29 = day29;
    }

    public BigDecimal getDay30() {
        return day30;
    }

    public void setDay30(BigDecimal day30) {
        this.day30 = day30;
    }
    
    /**
	 * 填满日期，以0填充
	 * @param list 需要填满的列表
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 填满后的列表
	 */
	public static List<KpiUserLtv> fill(List<KpiUserLtv> list,Date start,Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		
		Date temp;
		
		for(int i = 0; i < list.size(); i++) {
			KpiUserLtv elem = list.get(i);
			
			while((temp=cal.getTime()).before(elem.date)) {
				list.add(i++, new KpiUserLtv(temp));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(list.size()==0 && start.before(end)) {
			list.add(new KpiUserLtv(start));
		}
		
		if(list.size()>0) {
			while((temp=dateAdd(cal,list.get(list.size()-1).date)).before(end)) {
				list.add(new KpiUserLtv(temp));
			}
		}
		
		return list;
	}
	
	/**
	 * 将日期增加一天
	 * @param cal 日历
	 * @param target 目标日期
	 * @return 结果日期
	 */
	public static Date dateAdd(Calendar cal, Date target) {
		cal.setTime(target);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
}