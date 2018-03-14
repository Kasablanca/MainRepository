package com.min.concurrent.chapter5;

import java.util.concurrent.BlockingQueue;

public class TaskRunnable implements Runnable {

	BlockingQueue<Task> queue;

	public void run() {
		try {
			processTask(queue.take());
		} catch (InterruptedException e) {
			// restore interrupted status
			Thread.currentThread().interrupt();
		}
	}

	void processTask(Task task) {
		// Handle the task
	}

	interface Task {
	}

}
