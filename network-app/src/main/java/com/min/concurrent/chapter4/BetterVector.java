package com.min.concurrent.chapter4;

import java.util.Vector;

import com.min.annotation.ThreadSafe;

@ThreadSafe
public class BetterVector<E> extends Vector<E> {
	// When extending a serializable class, you should redefine serialVersionUID
	static final long serialVersionUID = -3963416950630760754L;

	public synchronized boolean putIfAbsent(E x) {
		boolean absent = !contains(x);
		if (absent)
			add(x);
		return absent;
	}
}
