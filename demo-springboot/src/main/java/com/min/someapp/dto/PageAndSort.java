package com.min.someapp.dto;

public class PageAndSort {

	/**页码，从0开始*/
	private int page;
	
	/**每页大小*/
	private int limit;
	
	/**排序字段*/
	private String field;
	
	/**排序方向(sort order)*/
	private String sord;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + limit;
		result = prime * result + page;
		result = prime * result + ((sord == null) ? 0 : sord.hashCode());
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
		PageAndSort other = (PageAndSort) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (limit != other.limit)
			return false;
		if (page != other.page)
			return false;
		if (sord == null) {
			if (other.sord != null)
				return false;
		} else if (!sord.equals(other.sord))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PageAndSort [page=" + page + ", limit=" + limit + ", field=" + field + ", sord=" + sord + "]";
	}
	
}
