package cn.lm.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/filter/*")
public class DefaultRequestFilter implements Filter {

    public DefaultRequestFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		System.out.println(this.getClass().getName());
		try {
			InputStream is = request.getInputStream();
			byte[] buf = new byte[4096];
			int len = 0;
			while((len=is.read(buf)) != -1){
				System.out.println(new String(buf,0,len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
