package com.min.test;

public class TestTenuringThreshold {
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		byte[] arr1, arr2, arr3;
		arr1 = new byte[_1MB/4];
		arr2 = new byte[4 * _1MB];
		arr3 = new byte[4 * _1MB];
		arr3 = null;
		arr3 = new byte[4 * _1MB];
	}
}
