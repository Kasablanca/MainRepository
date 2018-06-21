package com.syhd.ahriman.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof DailyTask) {
			DailyTask task = (DailyTask) bean;
			TaskWrapper taskWrapper = new TaskWrapper(task);
			taskExecutor.execute(taskWrapper);
			taskScheduler.schedule(taskWrapper, new CronTrigger("0 0 0 * * ?"));
		}
		return bean;
	}
	
	private class TaskWrapper implements Runnable {
		
		private DailyTask dailyTask;

		public TaskWrapper(DailyTask dailyTask) {
			this.dailyTask = dailyTask;
		}
		
		@Override
		public void run() {
			TransactionStatus transactionStatus = transactionManager.getTransaction(null);
			try {
				dailyTask.run();
				try {
					transactionManager.commit(transactionStatus);
				} catch (RuntimeException ignored) {}
			} catch (RuntimeException e) {
				e.printStackTrace();
				transactionManager.rollback(transactionStatus);
			}
		}
		
	}
	
}
