package com.syhd.ahriman.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private List<Runnable> taskList = new ArrayList<>();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		for(Runnable task : taskList) {
			task.run();
		}
	}
	
	/**
	 * 注册一个任务在项目启动完成时运行
	 * @param task 任务
	 */
	public void registerTask(Runnable task) {
		taskList.add(task);
	}

}
