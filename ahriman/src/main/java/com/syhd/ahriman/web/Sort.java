package com.syhd.ahriman.web;

/**
 * 封装前台layper插件的排序参数<br>
 * @author MIN.LEE
 *
 */
public class Sort {
	
	/**排序字段*/
	private String field;
	
	/**排序方向(sort order)*/
	private String sord;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
	
}
