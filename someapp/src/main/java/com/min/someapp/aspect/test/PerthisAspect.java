package com.min.someapp.aspect.test;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//@Component
@Aspect("perthis(com.min.someapp.aspect.test.SystemArchitecture.businessService())")
public class PerthisAspect {
	@SuppressWarnings("unused")
	private int someState;
	
	public PerthisAspect() {
		System.out.println(this.getClass().getName());
	}
	
	@Before("com.min.someapp.aspect.test.SystemArchitecture.businessService()")
	public void recordServiceUsage() {
		// ...
	}
}
