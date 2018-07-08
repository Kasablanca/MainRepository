package com.syhd.ahriman.utils;

import org.springframework.validation.BindingResult;

import com.syhd.ahriman.dto.Result;

public class ValidationUtils {

	public String validate(Object target) {
		
		return null;
	}
	
	/**
	 * 若有错误，则返回该错误，否则返回第二个参数
	 * @param error 验证错误对象
	 * @param result service返回结果
	 * @return 需要的结果
	 */
	public static Result determineResult(BindingResult error,Result result) {
		if(error.hasErrors()) {
			Result rst = Result.getErrorResult();
			rst.setMessage(error.getAllErrors().get(0).getDefaultMessage());
			return rst;
		} else {
			return result;
		}
	}
	
}
