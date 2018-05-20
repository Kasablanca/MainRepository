package com.syhd.ahriman.web;

import java.io.Serializable;
import java.util.List;

/**
 * 封装前台layer插件的列表返回数据<br>
 * @author MIN.LEE
 *
 */
public class TableData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**状态码*/
	private Integer code;
	
	/**响应消息*/
	private String msg;
	
	/**总记录数*/
	private Long count;
	
	/**实际数据*/
	private List<?> data;
	
	public TableData() {
		super();
	}
	
	/**
	 * msg字段默认为空字符串，code默认为0
	 * @param count
	 * @param data
	 */
	public TableData(Long count, List<?> data) {
		this.code = 0;
		this.msg = "";
		this.count = count;
		this.data = data;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	
}
