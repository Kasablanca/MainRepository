package cn.lm.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SystemAspect {
	
	@Around("execution(* cn.lm.service.TestTarget.*(..))")
	public Object helloworld(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("hello world!");
		return pjp.proceed();
	}
	
}
