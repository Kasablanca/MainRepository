package com.min.someapp.bean;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;

//@Component
public class TaskExecutorDemo {

	private class MessagePrinterTask implements Runnable {

		private String message;

		public MessagePrinterTask(String message) {
			this.message = message;
		}

		public void run() {
			System.out.println(message);
		}

	}
	
	@Resource
	private TaskExecutor defaultTaskExecutor;

	public void printMessages() {
		for(int i = 0; i < 25; i++) {
			defaultTaskExecutor.execute(new MessagePrinterTask("Message" + i));
		}
	}

}
