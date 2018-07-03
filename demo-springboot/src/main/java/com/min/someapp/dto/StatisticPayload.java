package com.min.someapp.dto;

import java.io.Serializable;

public class StatisticPayload implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Object response;
	private RequestPayload request;
	
	public StatisticPayload() {
		super();
	}

	public StatisticPayload(Object response, RequestPayload request) {
		super();
		this.response = response;
		this.request = request;
	}
	
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public RequestPayload getRequest() {
		return request;
	}
	public void setRequest(RequestPayload request) {
		this.request = request;
	}
	
}
