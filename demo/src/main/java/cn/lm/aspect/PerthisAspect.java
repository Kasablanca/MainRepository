package cn.lm.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//@Component
@Aspect("perthis(com.xyz.myapp.SystemArchitecture.businessService())")
public class PerthisAspect {
	@SuppressWarnings("unused")
	private int someState;
	
	public PerthisAspect() {
		System.out.println(this.getClass().getName());
	}
	
	@Before("com.xyz.myapp.SystemArchitecture.businessService()")
	public void recordServiceUsage() {
		// ...
	}
}
