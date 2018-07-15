package com.min.someapp.bean;

import org.springframework.context.SmartLifecycle;

//@Component
public class SmartLifecycleDemo implements SmartLifecycle {
	private boolean isRunning;
	
	public SmartLifecycleDemo() {
		System.out.println("constructor");
	}
	
	@Override
	public void start() {
		isRunning = true;
		System.out.println("start");
	}

	@Override
	public void stop() {
		isRunning = false;
		System.out.println("stop");
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int getPhase() {
		System.out.println("getPhase");
		return 10;
	}

	@Override
	public boolean isAutoStartup() {
		System.out.println("isAutoStartup");
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		System.out.println("stop runnable");
		callback.run();
	}

}
