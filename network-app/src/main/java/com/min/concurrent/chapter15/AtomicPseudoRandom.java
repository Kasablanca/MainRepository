package com.min.concurrent.chapter15;

import java.util.concurrent.atomic.AtomicInteger;

import com.min.annotation.ThreadSafe;

@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom {
	private AtomicInteger seed;

    AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            int nextSeed = calculateNext(s);
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }
}
