package com.min.test;

import java.util.LinkedList;
import java.util.List;

public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		List<String> list = new LinkedList<>();
		int i = 0;
		while(true)
			list.add(String.valueOf(i++).intern());
	}

}
