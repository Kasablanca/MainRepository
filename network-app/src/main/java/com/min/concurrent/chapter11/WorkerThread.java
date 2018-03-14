package com.min.concurrent.chapter11;

import java.util.concurrent.BlockingQueue;

public class WorkerThread {
	private final BlockingQueue<Runnable> queue;

	public WorkerThread(BlockingQueue<Runnable> queue) {
		this.queue = queue;
	}

	public void run() {
		while (true) {
			try {
				Runnable task = queue.take();
				task.run();
			} catch (InterruptedException e) {
				break; /* Allow thread to exit */
			}
		}
	}
}
