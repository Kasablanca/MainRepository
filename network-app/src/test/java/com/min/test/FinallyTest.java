package com.min.test;

public class FinallyTest {
	static int inc() {
		int x;
		try {
			x = 1;
			if(x == 1) throw new Exception("test!");
			return x;
		} catch (Exception e) {
			x = 2;
			return x;
		} finally {
			x = 3;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(inc());
	}
}
