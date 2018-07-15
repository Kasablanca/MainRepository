package com.min.someapp.aspect.test;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.someapp.dto.Result;

//@ControllerAdvice
public class DefaultControllerAspect extends ResponseEntityExceptionHandler {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@ResponseBody
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Result handleFileUploadException(){
		Result result = Result.getErrorResult();
		result.setMessage("文件大小超出限制");
		
		return result;
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Result result = Result.getErrorResult();
		result.setMessage("请求参数类型错误");
		try {
			return new ResponseEntity<Object>(objectMapper.writeValueAsString(result), headers, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("bad request", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
