package com.min.test;

public class NotInitialization {
	
	static {
		a = 2;
	}
	
	static int a = 1;
	
	static {
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		//1. System.out.println(SubClass.value);
		System.out.println(ConstClass.HELLOWORLD);
	}
}

class SuperClass {
	static {
		System.out.println("SuperClass init!");
	}
	
	public static int value = 123;
}

class SubClass extends SuperClass {
	static {
		System.out.println("SubClass init!");
	}
}
