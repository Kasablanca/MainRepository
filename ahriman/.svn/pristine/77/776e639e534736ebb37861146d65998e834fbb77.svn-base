package com.syhd.ahriman;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.aspectj.AnnotationCacheAspect;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.aspectj.AnnotationAsyncExecutionAspect;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

@SpringBootApplication
@EnableScheduling //启用任务调度注解(@Scheduled)
@EnableAsync(mode=AdviceMode.ASPECTJ) // 启用异步执行注解(@Async)
@EnableCaching(mode=AdviceMode.ASPECTJ) //启用缓存注解(@Cache**)
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ) // 启用事务注解(@Transactional)
@MapperScan("com.syhd.ahriman.dao.mapper") //扫描该包下面的所有接口为mapper，不加该注解则需要在每个接口上添加"@Mapper"注解
public class Application extends SpringBootServletInitializer {
	
	@Autowired
	public AnnotationAsyncExecutionAspect asyncAspect;
	
	@Autowired
	public AnnotationTransactionAspect transactionAspect;
	
	@Autowired
	public AnnotationCacheAspect cacheAspect;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	@Bean("taskExecutor")
	public ThreadPoolTaskExecutor getTaskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setKeepAliveSeconds(60);
		executor.setMaxPoolSize(1000);
		executor.setQueueCapacity(1000);
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				r.run(); // 若执行器队列已满，则让调用者线程直接运行
			}
		});
		return executor;
	}
	
	@Bean("taskScheduler")
	public ThreadPoolTaskScheduler getTaskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(1000);
		scheduler.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				r.run(); // 若调度器队列已满，则让调用者线程直接运行
			}
		});
		return scheduler;
	}

}
