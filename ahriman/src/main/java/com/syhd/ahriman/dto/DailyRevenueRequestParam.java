package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DailyRevenueRequestParam implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date start;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date end;
	
	private String[] platform;
	private Integer[] serverId;
	
	public DailyRevenueRequestParam() {
		super();
	}
	public DailyRevenueRequestParam(Date start, Date end, String[] platform, Integer[] serverId) {
		super();
		this.start = start;
		this.end = end;
		this.platform = platform;
		this.serverId = serverId;
	}
	public DailyRevenueRequestParam(DailyRevenueRequestParam other) {
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
		DailyRevenueRequestParam other = (DailyRevenueRequestParam) obj;
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
		return "DailyRevenueRequestParam [start=" + start + ", end=" + end + ", platform=" + Arrays.toString(platform)
				+ ", serverId=" + Arrays.toString(serverId) + "]";
	}
	
	/**
	 * 深拷贝
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		DailyRevenueRequestParam copy = (DailyRevenueRequestParam) super.clone();
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

}
