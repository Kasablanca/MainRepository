package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.syhd.ahriman.utils.ArrayUtils;
import com.syhd.ahriman.utils.DateUtils;

/**
 * 封装基本的请求参数
 * @author MIN.LEE
 *
 */
public class RequestPayload implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date start;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date end;
	
	private String[] platform;
	private Integer[] serverId;
	
	public RequestPayload() {
		super();
	}
	public RequestPayload(Date start, Date end, String[] platform, Integer[] serverId) {
		super();
		this.start = start;
		this.end = end;
		this.platform = platform;
		this.serverId = serverId;
	}
	public RequestPayload(RequestPayload other) {
		this.start = other.start;
		this.end = other.end;
		this.platform = other.platform;
		this.serverId = other.serverId;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String[] getPlatform() {
		return platform;
	}
	public void setPlatform(String[] platform) {
		this.platform = platform;
	}
	public Integer[] getServerId() {
		return serverId;
	}
	public void setServerId(Integer[] serverId) {
		this.serverId = serverId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + Arrays.hashCode(platform);
		result = prime * result + Arrays.hashCode(serverId);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestPayload other = (RequestPayload) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (!Arrays.equals(platform, other.platform))
			return false;
		if (!Arrays.equals(serverId, other.serverId))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RequestPayload [start=" + start + ", end=" + end + ", platform=" + Arrays.toString(platform)
				+ ", serverId=" + Arrays.toString(serverId) + "]";
	}
	
	/**
	 * 深拷贝
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		RequestPayload copy = (RequestPayload) super.clone();
		if(this.start != null) {
			copy.start = (Date) this.start.clone();
		}
		if(this.end != null) {
			copy.end = (Date) this.end.clone();
		}
		if(this.platform != null) {
			copy.platform = this.platform.clone();
		}
		if(this.serverId != null) {
			copy.serverId = this.serverId.clone();
		}
		
		return copy;
	}
	
	/**
	 * 预处理查询参数
	 * @param param 前台原始参数
	 * @param startOffset 开始日期默认多少天以前,不给此参数则默认一个月以前
	 * @return 处理后的参数
	 */
	public static RequestPayload prepare(RequestPayload param,Integer startOffset) {
		RequestPayload copy = new RequestPayload(param);
		
		String[] platforms = copy.getPlatform();
		if(platforms != null && ArrayUtils.contains(platforms, "-1")) {
			// 说明选择的全部平台，应该去掉此参数
			copy.setPlatform(null);
		}
		
		Integer[] serverIds = copy.getServerId();
		if(serverIds != null && ArrayUtils.contains(serverIds, -1)) {
			// 说明选择的全部服务器，应该去掉此参数
			copy.setServerId(null);
		}
		
		if(StringUtils.isEmpty(copy.getStart())) {
			// 若没给开始日期，则设置为上月同一天凌晨00:00:00
			if(startOffset == null) {
				copy.setStart(DateUtils.getOneMonthBeforeTime0());
			}
			Calendar now = Calendar.getInstance();
			now.set(Calendar.AM_PM, Calendar.AM);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MILLISECOND, 0);
			now.add(Calendar.DAY_OF_MONTH, startOffset);
			copy.setStart(now.getTime());
		}
		if(StringUtils.isEmpty(copy.getEnd()))	{
			// 若没给结束日期，则设置为今天凌晨00:00:00.000
			copy.setEnd(DateUtils.getTodayTime0());
		} else {
			// 若已给截止日期，则将截止日期设置为第二天凌晨
			Calendar now = Calendar.getInstance();
			now.setTime(copy.getEnd());
			now.add(Calendar.DAY_OF_MONTH, 1);
			copy.setEnd(now.getTime());
		}
		
		return copy;
	}
	
	/**
	 * 将结束日期还原，即减少1天
	 * @param param 前台查询参数
	 * @return 处理后的参数
	 */
	public static RequestPayload unPrepare(RequestPayload param) {
		Calendar now = Calendar.getInstance();
		now.setTime(param.end);
		now.add(Calendar.DAY_OF_MONTH, -1);
		param.setEnd(now.getTime());
		return param;
	}
	
	/**
	 * 是否包含今天
	 * @param param 前台查询参数
	 * @return true包含，否包含
	 */
	public static boolean containToday(RequestPayload param) {
		if(param.end.after(DateUtils.getTodayTime0())) {
			//在今天0点过后，说明包括今天
			return true;
		}
		return false;
	}

}
