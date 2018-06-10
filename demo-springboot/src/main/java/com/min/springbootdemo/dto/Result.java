package com.min.springbootdemo.dto;

public class Result {
	
	private Integer code;
	private Object data;
	private String message;
	
	public Result(Integer code, Object data, String message) {
		super();
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public Result() {
		super();
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static Result newErrorInstance() {
		return new Result(1,null,null);
	}
	
	public static Result newSuccessInstance() {
		return new Result(0,null,null);
	}

}
