package cn.lm.aspect;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alibaba.fastjson.JSON;

import cn.lm.Result;

@ControllerAdvice
public class DefaultControllerAspect extends ResponseEntityExceptionHandler {
	
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
		return new ResponseEntity<Object>(JSON.toJSONString(result), headers, HttpStatus.OK);
	}
	
}
