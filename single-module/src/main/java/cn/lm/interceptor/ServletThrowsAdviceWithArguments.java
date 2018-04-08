package cn.lm.interceptor;

import java.lang.reflect.Method;

import javax.servlet.ServletException;

import org.springframework.aop.ThrowsAdvice;

public class ServletThrowsAdviceWithArguments implements ThrowsAdvice {

	public void afterThrowing(Method m, Object[] args, Object target, ServletException ex) {
		// Do something with all arguments
	}

}
