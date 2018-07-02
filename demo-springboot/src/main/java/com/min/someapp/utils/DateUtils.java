package com.min.someapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期实用工具类
 * @author MIN.LEE
 *
 */
public class DateUtils {
	
	/**
	 * 获取今天凌晨00:00:00.000
	 * @return 今天凌晨00:00:00.000
	 */
	public static Date getTodayTime0() {
		return getTodayTime0(Calendar.getInstance());
	}
	
	/**
	 * 获取今天凌晨00:00:00.000
	 * ，用于同一上下文里多次获取时保持结果的统一性，不会修改参数的值
	 * @param now 现在时间
	 * @return 今天凌晨00:00:00.000
	 */
	public static Date getTodayTime0(Calendar now) {
		Calendar copy = (Calendar) now.clone();
		copy.set(Calendar.AM_PM, Calendar.AM);
		copy.set(Calendar.HOUR_OF_DAY, 0);
		copy.set(Calendar.MINUTE, 0);
		copy.set(Calendar.SECOND, 0);
		copy.set(Calendar.MILLISECOND, 0);
		return copy.getTime();
	}
	
	/**
	 * 获取昨天凌晨00:00:00
	 * @return 昨天凌晨00:00:00
	 */
	public static Date getYesterdayTime0() {
		return getYesterdayTime0(Calendar.getInstance());
	}
	
	/**
	 * 根据当前时间获取昨天凌晨00:00:00.000
	 * ，用于同一上下文里多次获取时保持结果的统一性
	 * @param now 现在时间
	 * @return 昨天凌晨00:00:00.000
	 */
	public static Date getYesterdayTime0(Calendar now) {
		Calendar copy = (Calendar) now.clone();
		copy.add(Calendar.DAY_OF_MONTH, -1);
		copy.set(Calendar.AM_PM, Calendar.AM);
		copy.set(Calendar.HOUR_OF_DAY, 0);
		copy.set(Calendar.MINUTE, 0);
		copy.set(Calendar.SECOND, 0);
		copy.set(Calendar.MILLISECOND, 0);
		return copy.getTime();
	}
	
	/**
	 * 根据当前时间获取明天凌晨00:00:00.000
	 * ，用于同一上下文里多次获取时保持结果的统一性
	 * @param now 现在时间
	 * @return 明天凌晨00:00:00.000
	 */
	public static Date getTommorowTime0(Calendar now) {
		Calendar copy = (Calendar) now.clone();
		copy.add(Calendar.DAY_OF_MONTH, 1);
		copy.set(Calendar.AM_PM, Calendar.AM);
		copy.set(Calendar.HOUR_OF_DAY, 0);
		copy.set(Calendar.MINUTE, 0);
		copy.set(Calendar.SECOND, 0);
		copy.set(Calendar.MILLISECOND, 0);
		return copy.getTime();
	}
	
	/**
	 * 根据当前时间获取明天凌晨00:00:00.000
	 * @return 明天凌晨00:00:00.000
	 */
	public static Date getTommorowTime0() {
		return getTommorowTime0(Calendar.getInstance());
	}
	
	/**
	 * 获取一个月前同一天凌晨00:00:00.000
	 * @return 一个月前同一天凌晨00:00:00.000
	 */
	public static Date getOneMonthBeforeTime0() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, -1);
		now.set(Calendar.AM_PM, Calendar.AM);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now.getTime();
	}
	
	/**
	 * 获取一周前凌晨00:00:00.000
	 * @return 一周前凌晨00:00:00.000
	 */
	public static Date getOneWeekBeforeTime0() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.WEEK_OF_MONTH, -1);
		now.set(Calendar.AM_PM, Calendar.AM);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now.getTime();
	}
	
	/**
	 * 将日期从java.util.Date转换为java.sql.Date，pattern为"yyyy-MM-dd"
	 * @param time java.util.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date conver2SqlDate(Date time){
		return java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(time));
	}
	
	/**
	 * 将日期从java.util.Date转换为java.sql.Timestamp，pattern为"yyyy-MM-dd HH:mm:ss"
	 * @param time java.util.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Timestamp conver2SqlTimestamp(Date time){
		return java.sql.Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
	}
	
	/**
	 * 日期加/减
	 * @param date 原始日期
	 * @param unit 日期单元，可以为天、周、月
	 * @param offset 偏移量，可以为负数
	 * @return 加/减后的新日期
	 */
	public static Date dateAdd(Date date,DateUnit unit,int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		switch(unit) {
		case DAY:
			cal.add(Calendar.DAY_OF_MONTH, offset);
			break;
		case WEEK:
			cal.add(Calendar.WEEK_OF_MONTH, offset);
			break;
		case MONTH:
			cal.add(Calendar.MONTH, offset);
			break;
		}
		
		return cal.getTime();
	}
	
	/**
	 * 判断是否整点
	 * @param target 被检查的日期
	 * @return true是正则，否则不是
	 */
	public static boolean isIntegralPoint(Date target) {
		Calendar now = Calendar.getInstance();
		now.setTime(target);
		if(now.get(Calendar.MINUTE) == 0 && now.get(Calendar.SECOND) == 0 && now.get(Calendar.MILLISECOND) == 0) {
			return true;
		}
		return false;
	}
	
	public static enum DateUnit {
		DAY,
		WEEK,
		MONTH;
	}
	
}
