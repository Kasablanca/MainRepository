package cn.lm.interceptor;

import java.util.Locale;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import cn.lm.CommonException;

@ControllerAdvice
public class CommonControllerAdvice {
	
	@ExceptionHandler(CommonException.class)
	public void exceptionHandler(){
		
	}
	
	@InitBinder()
	public void initBinder(WebDataBinder dataBinder, WebRequest webRequest, Locale locale){
		
	}
	
	@ModelAttribute()
	public void modelAttribute(){
		
	}
	
}
