package cn.lm.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class CountingAfterReturningAdvice implements AfterReturningAdvice {

	private int count;

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		++count;
	}

	public int getCount() {
		return count;
	}
}