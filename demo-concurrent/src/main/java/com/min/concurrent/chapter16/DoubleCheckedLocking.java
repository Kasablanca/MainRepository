package com.min.concurrent.chapter16;

import com.min.annotation.NotThreadSafe;

@NotThreadSafe
public class DoubleCheckedLocking {
	private static Resource resource;

	public static Resource getInstance() {
		if (resource == null) {
			synchronized (DoubleCheckedLocking.class) {
				if (resource == null)
					resource = new Resource();
			}
		}
		return resource;
	}

	static class Resource {
	}
}
