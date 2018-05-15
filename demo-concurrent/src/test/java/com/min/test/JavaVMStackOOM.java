package com.min.test;

public class JavaVMStackOOM {
	
	private void dontStop() {
		while(true);
	}
	
	public void stackLeakByThread() {
		while(true)
			new Thread() {
				public void run() {
					dontStop();
				};
			}.start();
	}

	public static void main(String[] args) {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}

}
