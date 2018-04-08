package cn.lm;

public class ResultObject {
	private String resultCode;
	private String resultMsg;
	private String resultCount;
	private Object resultData;
	
	public ResultObject() {
		super();
	}
	public ResultObject(String resultCode, String resultMsg, String resultCount, Object resultData) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.resultCount = resultCount;
		this.resultData = resultData;
	}
	public ResultObject(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getResultCount() {
		return resultCount;
	}
	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}
	public Object getResultData() {
		return resultData;
	}
	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
}
