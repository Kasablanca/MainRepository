package cn.lm.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//@Component
@Aspect("pertarget(com.xyz.myapp.SystemArchitecture.businessService())")
public class PerTargetAspect {
	@SuppressWarnings("unused")
	private int someState;
	
	public PerTargetAspect() {
		System.out.println(this.getClass().getName());
	}

	@Before("com.xyz.myapp.SystemArchitecture.businessService()")
	public void recordServiceUsage() {
		// ...
	}
}
