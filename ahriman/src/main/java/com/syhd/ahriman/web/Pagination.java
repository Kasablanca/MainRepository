package com.syhd.ahriman.web;

/**
 * 封装前台layer插件的列表分页参数<br>
 * @author MIN.LEE
 *
 */
public class Pagination {

	/**页码，从0开始*/
	private int page;
	
	/**每页大小*/
	private int limit;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
