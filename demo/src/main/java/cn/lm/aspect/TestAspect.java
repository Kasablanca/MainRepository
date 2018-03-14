package cn.lm.aspect;

import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;

import cn.lm.model.User;

//@Component
@Aspect
public class TestAspect {

	@Pointcut("execution(public * *(..))")
	private void anyPublicOperation() {}

	@Pointcut("within(com.xyz.someapp.trading..*)")
	private void inTrading() {}

	@Pointcut("anyPublicOperation() && inTrading()")
	private void tradingOperation() {}

	@Pointcut("this(com.xyz.service.AccountService)")
	public void thisAccountService(){}

	@Pointcut("target(com.xyz.service.AccountService)")
	public void targetAccountService(){}

	@Pointcut("args(java.io.Serializable)")
	public void args(){}

	@Pointcut("@target(org.springframework.transaction.annotation.Transactional)")
	public void targetTransactional(){}

	@Pointcut("@within(org.springframework.transaction.annotation.Transactional)")
	public void withinTransactional(){}

	@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
	public void annotationTransactional(){}

	@Pointcut("@args(com.xyz.security.Classified)")
	public void argsClassified(){}

	@Pointcut("bean(tradeService)")
	public void beanTradeService(){}

	@Pointcut("bean(*Service)")
	public void beanAnyService(){}

	@Before("com.xyz.myapp.SystemArchitecture.dataAccessOperation()")
	public void doAccessCheck() {
		// ...
	}

	@AfterReturning(pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation()",returning="retVal")
	public void doAccessCheck(Object retVal) {
		// ...
	}

	@AfterThrowing(pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation()",throwing="ex")
	public void doRecoveryActions(DataAccessException ex) {
		// ...
	}

	@After("com.xyz.myapp.SystemArchitecture.dataAccessOperation()")
	public void doReleaseLock() {
		// ...
	}

	@Around("com.xyz.myapp.SystemArchitecture.businessService()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		// start stopwatch
		Object retVal = pjp.proceed();
		// stop stopwatch
		return retVal;
	}
/*
	@Before("com.xyz.myapp.SystemArchitecture.dataAccessOperation() && args(account,..)")
	public void validateAccount(Account account) {
		// ...
	}

	@Before("accountDataAccessOperation(account)")
	public void validateAccount2(Account account) {
		// ...
	}*/

	@Before("com.xyz.lib.Pointcuts.anyPublicMethod() && @annotation(auditable)")
	public void audit(Auditable auditable) {
		auditable.value();
		// ...
	}

	@Before("execution(* ..Sample+.sampleGenericMethod(*)) && args(param)")
	public void beforeSampleMethod(User param) {
		// Advice implementation
	}

	@Before("execution(* ..Sample+.sampleGenericCollectionMethod(*)) && args(param)")
	public void beforeSampleMethod(Collection<User> param) {
		// Advice implementation
	}

	@Before(value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)", argNames="bean,auditable")
	public void audit(Object bean, Auditable auditable) {
		auditable.value();
		// ... use code and bean
	}

	@Before("com.xyz.lib.Pointcuts.anyPublicMethod()")
	public void audit(JoinPoint jp) {
		// ... use jp
	}

	@Around("execution(List<Account> find*(..)) && " +
			"com.xyz.myapp.SystemArchitecture.inDataAccessLayer() && " +
			"args(accountHolderNamePattern)")
	public Object preProcessQueryPattern(ProceedingJoinPoint pjp,
			String accountHolderNamePattern) throws Throwable {
		//String newPattern = preProcess(accountHolderNamePattern);
		String newPattern = accountHolderNamePattern;
		return pjp.proceed(new Object[] {newPattern});
	}

	@DeclareParents(value="com.xzy.myapp.service.*+", defaultImpl=DefaultUsageTracked.class)
	public static UsageTracked mixin;

	@Before("com.xyz.myapp.SystemArchitecture.businessService() && this(usageTracked)")
	public void recordUsage(UsageTracked usageTracked) {
		usageTracked.incrementUseCount();
	}
}
