package com.min.concurrent.chapter4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.min.annotation.NotThreadSafe;
import com.min.annotation.ThreadSafe;

@NotThreadSafe
public class BadListHelper<E> {
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public synchronized boolean putIfAbsent(E x) {
		boolean absent = !list.contains(x);
		if (absent)
			list.add(x);
		return absent;
	}
}

@ThreadSafe
class GoodListHelper <E> {
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public boolean putIfAbsent(E x) {
		synchronized (list) {
			boolean absent = !list.contains(x);
			if (absent)
				list.add(x);
			return absent;
		}
	}
}