package com.min.concurrent.chapter15;

import com.min.annotation.ThreadSafe;

@ThreadSafe
public class CasCounter {
	private SimulatedCAS value;

	public int getValue() {
		return value.get();
	}

	public int increment() {
		int v;
		do {
			v = value.get();
		} while (v != value.compareAndSwap(v, v + 1));
		return v + 1;
	}
}
