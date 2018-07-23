package com.min.someapp.aspect.test;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
//@Component
public class MixinAspect {
	
	@DeclareParents(value="com.min.someapp.service..Test*", defaultImpl=DefaultUsageTracked.class)
	public static UsageTracked mixin;

	@Before("execution(* com.min.someapp.service..*.*(..)) && this(usageTracked)")
	public void recordUsage(UsageTracked usageTracked) {
		usageTracked.incrementUseCount();
	}
}
