package cn.lm.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class LockMixin extends DelegatingIntroductionInterceptor implements Lockable {

	private static final long serialVersionUID = 1L;
	private boolean locked;

	@Override
	public void lock() {
		this.locked = true;
	}

	@Override
	public void unlock() {
		this.locked = false;
	}

	@Override
	public boolean locked() {
		return this.locked;
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		if (locked() && mi.getMethod().getName().indexOf("set") == 0) {
			throw new LockedException();
		}
		return super.invoke(mi);
	}
}
