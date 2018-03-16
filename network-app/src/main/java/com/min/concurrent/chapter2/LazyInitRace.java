package com.min.concurrent.chapter2;

import com.min.annotation.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {
	private ExpensiveObject instance = null;

	public ExpensiveObject getInstance() {
		if (instance == null)
			instance = new ExpensiveObject();
		return instance;
	}
}

class ExpensiveObject { }