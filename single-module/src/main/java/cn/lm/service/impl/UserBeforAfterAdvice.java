package cn.lm.service.impl;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

@Component
public class UserBeforAfterAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("returnValue:"+returnValue+",method:"+method.toGenericString()+",args:"+args);
	}

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("method:"+method.getName()+",args:"+args+",target:"+target);
	}

}
