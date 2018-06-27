package com.syhd.ahriman.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * 任务Bean后置处理器
 * @author MIN.LEE
 *
 */
@Component
public class TaskBeanPostProcessor implements BeanPostProcessor {

	private static final Logger logger = Logger.getLogger(TaskBeanPostProcessor.class);
	
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof DailyTask) {
			DailyTask task = (DailyTask) bean;
			DailyTaskWrapper taskWrapper = new DailyTaskWrapper(task);
			taskWrapper.run();
			taskScheduler.schedule(taskWrapper, new CronTrigger("0 0 0 * * ?"));
		}
		
		Map<Method,CronTask> map = getMethodAnnotation(bean,CronTask.class);
		for(Map.Entry<Method,CronTask> entry : map.entrySet()) {
			CronTaskWrapper taskWrapper = new CronTaskWrapper(bean,entry.getKey());
			taskWrapper.run();
			taskScheduler.schedule(taskWrapper, new CronTrigger(entry.getValue().value()));
		}
		
		return bean;
	}
	
	private <T extends Annotation> Map<Method,T> getMethodAnnotation(Object target,Class<T> annotation) {
		Method[] methods = target.getClass().getDeclaredMethods();
		
		Map<Method,T> map = new HashMap<>();
		
		for(Method method : methods) {
			T[] array = method.getAnnotationsByType(annotation);
			if(array.length == 1) {
				map.put(method, array[0]);
			}
		}
		
		return map;
	}
	
	private class CronTaskWrapper implements Runnable {
		private Object object;
		private Method method;
		
		public CronTaskWrapper(Object object,Method method) {
			this.object = object;
			this.method = method;
		}

		@Override
		public void run() {
			TransactionStatus transactionStatus = transactionManager.getTransaction(null);
			try {
				method.invoke(object);
				try {
					transactionManager.commit(transactionStatus);
				} catch (RuntimeException ignored) {
					ignored.printStackTrace();
					logger.error("提交事务失败", ignored.getCause());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("定时任务运行失败", e.getCause());
				transactionManager.rollback(transactionStatus);
			}
		}
	}
	
	private class DailyTaskWrapper implements Runnable {
		
		private DailyTask dailyTask;

		public DailyTaskWrapper(DailyTask dailyTask) {
			this.dailyTask = dailyTask;
		}
		
		@Override
		public void run() {
			TransactionStatus transactionStatus = transactionManager.getTransaction(null);
			try {
				dailyTask.run();
				try {
					transactionManager.commit(transactionStatus);
				} catch (RuntimeException ignored) {
					ignored.printStackTrace();
					logger.error("提交事务失败", ignored.getCause());
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.error("定时任务运行失败", e.getCause());
				transactionManager.rollback(transactionStatus);
			}
		}
		
	}
	
}
