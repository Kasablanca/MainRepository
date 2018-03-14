package com.min.concurrent.chapter4;

import com.min.annotation.Immutable;

@Immutable
public class Point {
	public final int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
