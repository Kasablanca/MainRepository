package com.min.test;

import org.apache.mina.core.buffer.IoBuffer;

public class TestCase3 {

	public static void main(String[] args) {
		IoBuffer buffer = IoBuffer.allocate(16);
		buffer.setAutoShrink(true);
		buffer.put((byte)1);
		System.out.println("Initial Buffer capacity = "+buffer.capacity());
		buffer.shrink();
		System.out.println("Initial Buffer capacity after shrink = "+buffer.capacity());
		buffer.capacity(32);
		System.out.println("Buffer capacity after incrementing capacity to 32 = "+buffer.capacity());
		
		System.out.println("Buffer capacity after shrink= "+buffer.compact().capacity());
	}

}
