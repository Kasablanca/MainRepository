package com.min.springbootdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLogAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

	@Pointcut("within(com.min.springbootdemo.web.controller.*)")
	public void controllerLayer() {
		// controller layer pointcut
	}
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMappingMethod() {
		
	}
	
	@Before("controllerLayer() && requestMappingMethod()")
	public void doLog(JoinPoint jp) {
		logger.trace(jp.toShortString());
	}
	
}
