package com.min.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestCase2 {
	
	static interface ITest {
		void print();
	}
	
	public static void main(String[] args) {
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
	
}

class Singleton {
	
	private static class Holder {
		static Singleton one = new Singleton();
	}
	
	public Singleton getInstance() {
		return Holder.one;
	}
}