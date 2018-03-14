package com.min.test;

import java.util.LinkedList;
import java.util.List;

public class TestCase {
	static Object o;
	
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(3000);
		fillHeap(1000);
	}
	
	static class OOMObject {
		byte[] placeHolder = new byte[64 * 1024];
	}
	
	static void fillHeap(int num) throws InterruptedException {
		List<OOMObject> list = new LinkedList<>();
		for(int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();
	}
}
