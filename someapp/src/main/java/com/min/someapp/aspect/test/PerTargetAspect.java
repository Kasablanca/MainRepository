package com.min.someapp.aspect.test;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//@Component
@Aspect("pertarget(com.min.someapp.aspect.test.SystemArchitecture.businessService())")
public class PerTargetAspect {
	@SuppressWarnings("unused")
	private int someState;
	
	public PerTargetAspect() {
		System.out.println(this.getClass().getName());
	}

	@Before("com.min.someapp.aspect.test.SystemArchitecture.businessService()")
	public void recordServiceUsage() {
		// ...
	}
}
