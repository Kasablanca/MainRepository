package com.ronsoft.books.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class SelectSocketsThreadPool extends SelectSockets {
	
	private static final int MAX_THREADS = 5;
	private ThreadPool pool = new ThreadPool(MAX_THREADS);

	// -------------------------------------------------------------
	public static void main(String[] argv) throws Exception {
		new SelectSocketsThreadPool().go(argv);
	}

	// -------------------------------------------------------------
	protected void readDataFromSocket(SelectionKey key) throws Exception {
		WorkerThread worker = pool.getWorker();

		if (worker == null) {
			// No threads available. Do nothing. The selection
			// loop will keep calling this method until a
			// thread becomes available. This design could
			// be improved.
			return;
		}

		// Invoking this wakes up the worker thread, then returns
		worker.serviceChannel(key);
	}

	// ---------------------------------------------------------------
	private class ThreadPool {
		List<Thread> idle = new LinkedList<>();

		ThreadPool(int poolSize) {
			// Fill up the pool with worker threads
			for (int i = 0; i < poolSize; i++) {
				WorkerThread thread = new WorkerThread(this);

				// Set thread name for debugging. Start it.
				thread.setName("Worker" + (i + 1));
				thread.start();

				idle.add(thread);
			}
		}

		WorkerThread getWorker() {
			WorkerThread worker = null;

			synchronized (idle) {
				if (idle.size() > 0) {
					worker = (WorkerThread) idle.remove(0);
				}
			}

			return (worker);
		}

		void returnWorker(WorkerThread worker) {
			synchronized (idle) {
				idle.add(worker);
			}
		}
	}

	private class WorkerThread extends Thread {
		private ByteBuffer buffer = ByteBuffer.allocate(1024);
		private ThreadPool pool;
		private SelectionKey key;

		WorkerThread(ThreadPool pool) {
			this.pool = pool;
		}

		// Loop forever waiting for work to do
		public synchronized void run() {
			System.out.println(this.getName() + " is ready");
			while (true) {
				try {
					// Sleep and release object lock
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					// Clear interrupt status
					Thread.interrupted();
				}

				if (key == null) {
					continue; // just in case
				}

				System.out.println(this.getName() + " has been awakened");
				try {
					drainChannel(key);
				} catch (Exception e) {
					System.out.println("Caught '" + e + "' closing channel");

					// Close channel and nudge selector
					try {
						key.channel().close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					key.selector().wakeup();
				}

				key = null;

				// Done. Ready for more. Return to pool
				this.pool.returnWorker(this);
			}
		}

		synchronized void serviceChannel(SelectionKey key) {
			this.key = key;

			key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
			this.notify(); // Awaken the thread
		}

		void drainChannel(SelectionKey key) throws Exception {
			SocketChannel channel = (SocketChannel) key.channel();
			int count;

			buffer.clear(); // Empty buffer

			// Loop while data is available; channel is nonblocking
			while ((count = channel.read(buffer)) > 0) {
				buffer.flip(); // make buffer readable

				// Send the data; may not go all at once
				while (buffer.hasRemaining()) {
					channel.write(buffer);
				}

				// WARNING: the above loop is evil.
				// See comments in superclass.
				buffer.clear(); // Empty buffer
			}

			if (count < 0) {
				// Close channel on EOF; invalidates the key
				channel.close();
				return;
			}

			// Resume interest in OP_READ
			key.interestOps(key.interestOps() | SelectionKey.OP_READ);

			// Cycle the selector so this key is active again
			key.selector().wakeup();
		}
	}

}
