package cn.lm;

public class Result {
	
	public static final Integer DEFAULT_SUCCESS_CODE = 0;
	public static final Integer DEFAULT_ERROR_CODE = 1;
	
	public static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	public static final String DEFAULT_ERROR_MESSAGE = "操作成功";
	
	private Integer code;
	private Object data;
	private String message;
	
	public Result() {
		super();
	}
	public Result(Integer code, Object data, String message) {
		super();
		this.code = code;
		this.data = data;
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	
	public static Result getSuccessResult(){
		return new Result(DEFAULT_SUCCESS_CODE,null,DEFAULT_SUCCESS_MESSAGE);
	}
	
	public static Result getErrorResult(){
		return new Result(DEFAULT_ERROR_CODE,null,DEFAULT_ERROR_MESSAGE);
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
	
}
