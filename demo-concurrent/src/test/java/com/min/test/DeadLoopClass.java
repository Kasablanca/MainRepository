package com.min.test;

public class DeadLoopClass {
	static {
		if(true) {
			System.out.println("static block");
			while(true);
		}
	}
	
	public static void main(String[] args) {
		Runnable script = new Runnable() {
			@Override
			public void run() {
				System.out.println("thread start");
				@SuppressWarnings("unused")
				DeadLoopClass dlc = new DeadLoopClass();
				System.out.println("thread end");
			}
		};
		
		new Thread(script).start();
		new Thread(script).start();
	}
}
