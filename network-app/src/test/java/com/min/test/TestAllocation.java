package com.min.test;

public class TestAllocation {
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		byte[] arr1, arr2, arr3, arr4;
		arr1 = new byte[2 * _1MB];
		arr2 = new byte[2 * _1MB];
		arr3 = new byte[2 * _1MB];
		arr4 = new byte[4 * _1MB];
	}
}
