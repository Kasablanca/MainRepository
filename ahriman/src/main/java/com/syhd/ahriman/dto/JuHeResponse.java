package com.syhd.ahriman.dto;

import java.io.Serializable;

/**
 * 聚合数据( http://www.juhe.cn )汇率查询结果
 * @author MIN.LEE
 *
 */
public class JuHeResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer error_code;
	private String reason;
	private Object result;
	
	public Integer getError_code() {
		return error_code;
	}
	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
