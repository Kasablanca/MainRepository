package com.min.someapp.dto;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + limit;
		result = prime * result + page;
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
		Pagination other = (Pagination) obj;
		if (limit != other.limit)
			return false;
		if (page != other.page)
			return false;
		return true;
	}
	
}
