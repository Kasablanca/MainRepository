package com.min.test;

import java.util.LinkedList;
import java.util.List;

public class HeapOOM {
	static class BigObject {
		double[] arr = new double[1000];
	}
	
	public static void main(String[] args) {
		List<BigObject> list = new LinkedList<>();
		while(true)
			list.add(new BigObject());
	}

}
