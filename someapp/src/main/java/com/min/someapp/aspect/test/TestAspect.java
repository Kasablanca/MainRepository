package com.min.someapp.aspect.test;

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

	@Pointcut("@args(org.springframework.validation.annotation.Validated)")
	public void argsClassified(){}
/*
	@Pointcut("bean(tradeService)")
	public void beanTradeService(){}

	@Pointcut("bean(*Service)")
	public void beanAnyService(){}
*/
	@Before("com.min.someapp.aspect.test.SystemArchitecture.dataAccessOperation()")
	public void doAccessCheck() {
		// ...
	}

	@AfterReturning(pointcut="com.min.someapp.aspect.test.SystemArchitecture.dataAccessOperation()",returning="retVal")
	public void doAccessCheck(Object retVal) {
		// ...
	}

	@AfterThrowing(pointcut="com.min.someapp.aspect.test.SystemArchitecture.dataAccessOperation()",throwing="ex")
	public void doRecoveryActions(DataAccessException ex) {
		// ...
	}

	@After("com.min.someapp.aspect.test.SystemArchitecture.dataAccessOperation()")
	public void doReleaseLock() {
		// ...
	}

	@Around("com.min.someapp.aspect.test.SystemArchitecture.businessService()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		// start stopwatch
		Object retVal = pjp.proceed();
		// stop stopwatch
		return retVal;
	}
/*
	@Before("com.min.someapp.aspect.test.SystemArchitecture.dataAccessOperation() && args(account,..)")
	public void validateAccount(Account account) {
		// ...
	}

	@Before("accountDataAccessOperation(account)")
	public void validateAccount2(Account account) {
		// ...
	}*/
/*
	@Before("argsClassified() && @annotation(auditable)")
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
	}*/
/*
	@Before(value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)", argNames="bean,auditable")
	public void audit(Object bean, Auditable auditable) {
		auditable.value();
		// ... use code and bean
	}

	@Before("com.xyz.lib.Pointcuts.anyPublicMethod()")
	public void audit(JoinPoint jp) {
		// ... use jp
	}*/

	@Around("execution(com.min.someapp.dto.TableData get*(..)) && " +
			"com.min.someapp.aspect.test.SystemArchitecture.inDataAccessLayer() && " +
			"args(accountHolderNamePattern)")
	public Object preProcessQueryPattern(ProceedingJoinPoint pjp,
			String accountHolderNamePattern) throws Throwable {
		//String newPattern = preProcess(accountHolderNamePattern);
		String newPattern = accountHolderNamePattern;
		return pjp.proceed(new Object[] {newPattern});
	}

	@DeclareParents(value="com.min.someapp.service.*+", defaultImpl=DefaultUsageTracked.class)
	public static UsageTracked mixin;

	@Before("com.min.someapp.aspect.test.SystemArchitecture.businessService() && this(usageTracked)")
	public void recordUsage(UsageTracked usageTracked) {
		usageTracked.incrementUseCount();
	}
}
