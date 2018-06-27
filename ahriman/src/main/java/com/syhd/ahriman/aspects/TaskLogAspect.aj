package com.syhd.ahriman.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.stereotype.Component;

@Aspect
@Component
/**
 * 任务日志切面，仅供测试用
 * @author MIN.LEE
 *
 */
public class TaskLogAspect {
	
	private static final Logger logger = Logger.getLogger(TaskLogAspect.class);

	@Pointcut("@annotation(com.syhd.ahriman.service.CronTask)")
	public void annotationWithCronTask(){}
	
	@Around("annotationWithCronTask()")
	public Object aroundAnnotationWithCronTask(ProceedingJoinPoint pjp) throws Throwable {
		String methodName = pjp.getStaticPart().toLongString();
		logger.log(Level.INFO, methodName+" start");
		Object result = pjp.proceed();
		logger.log(Level.INFO, methodName+" end");
		return result;
	}
	
}
