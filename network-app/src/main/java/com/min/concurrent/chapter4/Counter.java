package com.min.concurrent.chapter4;

import com.min.annotation.GuardedBy;
import com.min.annotation.ThreadSafe;

@ThreadSafe
public final class Counter {
	@GuardedBy("this") private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE)
            throw new IllegalStateException("counter overflow");
        return ++value;
    }
}
