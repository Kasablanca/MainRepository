package com.min.concurrent.chapter16;

import com.min.annotation.ThreadSafe;

@ThreadSafe
public class ResourceFactory {
	private static class ResourceHolder {
		public static Resource resource = new Resource();
	}

	public static Resource getResource() {
		return ResourceFactory.ResourceHolder.resource;
	}

	static class Resource {
	}
}
