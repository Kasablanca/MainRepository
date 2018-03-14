package com.min.concurrent.chapter5;

public interface Computable<A, V> {
	 V compute(A arg) throws InterruptedException;
}
