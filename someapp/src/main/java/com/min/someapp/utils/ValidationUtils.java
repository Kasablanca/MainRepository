package com.min.someapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.validation.BindingResult;

import com.min.someapp.dto.Result;

public class ValidationUtils {
	
	private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

	public String validate(Object target) {
		
		return null;
	}
	
	/**
	 * 若有错误，则返回该错误，否则返回第二个参数
	 * @param error 验证错误对象
	 * @param result service返回结果
	 * @return 需要的结果
	 */
	public static Result decideResult(BindingResult error,Result result) {
		if(error.hasErrors()) {
			Result rst = Result.getErrorResult();
			rst.setMessage(error.getAllErrors().get(0).getDefaultMessage());
			return rst;
		} else {
			return result;
		}
	}
	
	public static <T> List<String> validate(T t, Class<?>... mclass){
		Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, mclass);
        List<String> messageList = new ArrayList<>(constraintViolations.size());
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
	}
	
}
