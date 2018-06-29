package com.syhd.ahriman.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(TaskBeanPostProcessor.class);
	
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	private ApplicationStartedListener applicationStartedListener;
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Map<Method,CronTask> map = getMethodAnnotation(bean,CronTask.class);
		for(Map.Entry<Method,CronTask> entry : map.entrySet()) {
			// 项目启动时同步初始运行一次
			new CronTaskWrapper(bean,entry.getKey(),true).run();
			
			// 项目启动完成后再设置定时任务
			applicationStartedListener.registerTask(new Runnable() {
				public void run() {
					taskScheduler.schedule(
						new CronTaskWrapper(bean,entry.getKey(),false), 
						new CronTrigger(entry.getValue().value()));
				}
			});
		}
		
		return bean;
	}
	
	/**
	 * 获取方法注解
	 * @param target 注解所属对象
	 * @param annotation 注解类型
	 * @return 包含方法和注解实例的map
	 */
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
		/**是否初始任务*/
		private boolean init;
		
		/**
		 * 构建一个任务运行被CronTask注解的bean方法
		 * @param object spring bean
		 * @param method bean 方法
		 * @param init 是否初始任务
		 */
		public CronTaskWrapper(Object object,Method method,boolean init) {
			this.object = object;
			this.method = method;
			this.init = init;
		}

		@Override
		public void run() {
			String temp = init ? "启动时执行任务：" : "定时执行任务：";
			
			TransactionStatus transactionStatus = transactionManager.getTransaction(null); // 开始事务
			try {
				logger.info(temp + method.toGenericString() + " 开始！");
				method.invoke(object);
				logger.info(temp + method.toGenericString() + " 结束！");
				
				try {
					transactionManager.commit(transactionStatus); // 提交事务
				} catch (RuntimeException ignored) {
					logger.error("提交事务失败", ignored.getCause());
				}
			} catch (Exception e) {
				logger.error(temp + method.toGenericString() + " 失败！", e.getCause());
				
				transactionManager.rollback(transactionStatus); // 回滚事务
			}
		}
	}
	
}
