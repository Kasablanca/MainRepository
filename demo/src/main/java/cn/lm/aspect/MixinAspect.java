package cn.lm.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MixinAspect {
	@DeclareParents(value="cn.lm.service.impl..*", defaultImpl=DefaultUsageTracked.class)
	public static UsageTracked mixin;

	@Before("execution(* cn.lm.service.impl.*Impl.*(..)) && this(usageTracked)")
	public void recordUsage(UsageTracked usageTracked) {
		usageTracked.incrementUseCount();
	}
}
