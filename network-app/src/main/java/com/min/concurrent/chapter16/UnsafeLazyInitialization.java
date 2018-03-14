package com.min.concurrent.chapter16;

import com.min.annotation.NotThreadSafe;

@NotThreadSafe
public class UnsafeLazyInitialization {
	private static Resource resource;

	public static Resource getInstance() {
		if (resource == null)
			resource = new Resource(); // unsafe publication
		return resource;
	}

	static class Resource {
	}
}
