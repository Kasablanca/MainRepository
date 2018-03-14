package com.min.concurrent.chapter4;

import com.min.annotation.GuardedBy;

public class PrivateLock {
	private final Object myLock = new Object();
    @GuardedBy("myLock") Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
        }
    }
    
    interface Widget {
    }
}
