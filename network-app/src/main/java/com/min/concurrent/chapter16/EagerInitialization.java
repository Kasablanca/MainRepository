package com.min.concurrent.chapter16;

import com.min.annotation.ThreadSafe;

@ThreadSafe
public class EagerInitialization {
	private static Resource resource = new Resource();

	public static Resource getResource() {
		return resource;
	}

	static class Resource {
	}
}
