package com.min.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestCase2 {
	
	public static void main(String[] args) throws InterruptedException {
		test5();
	}
	
	static ReentrantLock lock = new ReentrantLock(false);
	static Condition notFull = lock.newCondition();
	static Condition notEmpty = lock.newCondition();
	
	static final int SIZE = 10;
	
	static List<Integer> ids = new ArrayList<>();
	
	static void test6() throws InterruptedException {
		lock.lock();
		try {
			if(ids.size() == 0) {
				boolean successed = notEmpty.await(3, TimeUnit.SECONDS);
				if(!successed) {
					System.out.println("insert attemption failure");
				} else {
					ids.add(new Random().nextInt(10));
				}
			}
			if(ids.size() == SIZE) {
				boolean successed = notFull.await(3, TimeUnit.SECONDS);
				if(!successed) {
					System.out.println("remove attemption failure");
				} else {
					ids.remove(0);
				}
			}
		} finally {
			lock.unlock();
		}
	}
	
	static volatile int n = 0;
	
	static void test5() {
		ThreadPoolExecutor service = /*Executors.newFixedThreadPool(5)*/ 
				new ThreadPoolExecutor(5, 5, 5000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5),
						new RejectedExecutionHandler() {
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println("bad submit!");
					}
				});
		for(int i = 0; i < 10; i++) {
			service.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println(n++);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		service.shutdown();
	}
	
	static int i = 0;
	
	static Semaphore s1 = new Semaphore(1);
	static Semaphore s2 = new Semaphore(1);
	
	static class MainThread extends Thread {
		public void run() {
			while(true) {
				try {
					s1.acquire();
					if(i == 10)
						break;
					System.out.println("total cycle: "+i);
					System.out.print("main thread: ");
					for(int j = 0; j < 10; j++) {
						System.out.print(j+" ");
					}
					System.out.println();
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				} finally {
					s2.release();
				}
			}
		}
	}
	
	static class SubThread extends Thread {
		public void run() {
			while(true) {
				try {
					s2.acquire();
					if(i == 10)
						break;
					System.out.print("sub thread: ");
					for(int j = 0; j < 15; j++) {
						System.out.print(j+" ");
					}
					System.out.println();
					i++;
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				} finally {
					s1.release();
				}
			}
		}
	}
	
	static void test4() throws InterruptedException {
		s2.acquire();
		new MainThread().start();
		new SubThread().start();
	}
	
	static void test3() throws InterruptedException {
		for(int i = 0; i < 10; i++) {
			System.out.println("total cycle: "+i);
			System.out.print("main thread: ");
			for(int j = 0; j < 10; j++) {
				System.out.print(j+" ");
			}
			System.out.println();
			
			Thread subThread = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.print("sub thread: ");
					for(int j = 0; j < 15; j++) {
						System.out.print(j+" ");
					}
					System.out.println();
				}
				
			});
			subThread.start();
			subThread.join();
		}
	}
	
	static void test2() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println(new Date());
			}
		}, 1000, 1000*2);
	}
	
	static interface ITest {
		void print();
	}
	
	static void test1() {
		final ITest target = new ITest() {
			@Override
			public void print() {
				System.out.println("Hello");
			}
		};
		
		Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] {ITest.class},new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object result = method.invoke(target, args);
				System.out.println("World!");
				return result;
			}
		});
		
		ITest obj = (ITest) proxy;
		obj.print();
		System.out.println(obj instanceof Proxy);
	}
	
	static class Singleton {
		
		private static class Holder {
			static Singleton one = new Singleton();
		}
		
		public Singleton getInstance() {
			return Holder.one;
		}
	}
	
}