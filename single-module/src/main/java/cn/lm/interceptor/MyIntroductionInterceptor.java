package cn.lm.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

public class MyIntroductionInterceptor implements IntroductionInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return null;
	}

	@Override
	public boolean implementsInterface(Class<?> intf) {
		return false;
	}

}
