package com.min.concurrent.chapter2;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.min.annotation.ThreadSafe;

@ThreadSafe
public class CountingFactorizer extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	private final AtomicLong count = new AtomicLong(0);

	public long getCount() { return count.get(); }

	public void service(ServletRequest req, ServletResponse resp) {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = factor(i);
		count.incrementAndGet();
		encodeIntoResponse(resp, factors);
	}

	void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {}
	BigInteger extractFromRequest(ServletRequest req) {return null; }
	BigInteger[] factor(BigInteger i) { return null; }

}
