package com.min.concurrent.chapter2;

import java.math.BigInteger;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.min.annotation.GuardedBy;
import com.min.annotation.ThreadSafe;

@ThreadSafe
public class SynchronizedFactorizer extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	@GuardedBy("this") private BigInteger lastNumber;
	@GuardedBy("this") private BigInteger[] lastFactors;

	public synchronized void service(ServletRequest req, ServletResponse resp) {
		BigInteger i = extractFromRequest(req);
		if (i.equals(lastNumber))
			encodeIntoResponse(resp, lastFactors);
		else {
			BigInteger[] factors = factor(i);
			lastNumber = i;
			lastFactors = factors;
			encodeIntoResponse(resp, factors);
		}
	}

	void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
	}

	BigInteger extractFromRequest(ServletRequest req) {
		return new BigInteger("7");
	}

	BigInteger[] factor(BigInteger i) {
		// Doesn't really factor
		return new BigInteger[] { i };
	}

}
