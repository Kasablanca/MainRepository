package com.min.concurrent.chapter8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadlock {
	ExecutorService exec = Executors.newSingleThreadExecutor();

	public class LoadFileTask implements Callable<String> {
		@SuppressWarnings("unused")
		private final String fileName;

		public LoadFileTask(String fileName) {
			this.fileName = fileName;
		}

		public String call() throws Exception {
			// Here's where we would actually read the file
			return "";
		}
	}

	public class RenderPageTask implements Callable<String> {
		public String call() throws Exception {
			Future<String> header, footer;
			header = exec.submit(new LoadFileTask("header.html"));
			footer = exec.submit(new LoadFileTask("footer.html"));
			String page = renderBody();
			// Will deadlock -- task waiting for result of subtask
			return header.get() + page + footer.get();
		}

		private String renderBody() {
			// Here's where we would actually render the page
			return "";
		}
	}
}