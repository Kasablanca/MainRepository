package com.min.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.min.dao.entity.User;

@WebFilter("/test/*")
public class DefaultFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("filtered!");
		request.setAttribute("user", new User("MR.RIGHT",(byte) 1,"programonkey@sina.com"));
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
