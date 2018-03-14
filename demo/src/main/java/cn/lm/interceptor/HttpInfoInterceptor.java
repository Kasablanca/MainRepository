package cn.lm.interceptor;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HttpInfoInterceptor implements HandlerInterceptor {
	public String interceptorName;
	
	public HttpInfoInterceptor(String interceptorName){
		this.interceptorName=interceptorName;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("----------------------------------------------------");
		System.out.println("request info:");
		System.out.println("request line:"+request.getMethod()+" "+request.getRequestURI()+" "+request.getProtocol());
		System.out.println("AuthType:"+request.getAuthType());
		System.out.println("CharacterEncoding:"+request.getCharacterEncoding());
		System.out.println("ContentLength:"+request.getContentLength());
		System.out.println("ContentType:"+request.getContentType());
		System.out.println("ContextPath:"+request.getContextPath());
		System.out.println("LocalAddr:"+request.getLocalAddr());
		System.out.println("LocalName:"+request.getLocalName());
		System.out.println("LocalPort:"+request.getLocalPort());
		System.out.println("PathInfo:"+request.getPathInfo());
		System.out.println("PathTranslated:"+request.getPathTranslated());
		System.out.println("QueryString:"+request.getQueryString());
		System.out.println("RemoteAddr:"+request.getRemoteAddr());
		System.out.println("RemoteHost:"+request.getRemoteHost());
		System.out.println("RemotePort:"+request.getRemotePort());
		System.out.println("RemoteUser:"+request.getRemoteUser());
		System.out.println("RequestedSessionId:"+request.getRequestedSessionId());
		System.out.println("Scheme:"+request.getScheme());
		System.out.println("ServerName:"+request.getServerName());
		System.out.println("ServerPort:"+request.getServerPort());
		System.out.println("ServletPath:"+request.getServletPath());
		System.out.println("AsyncStarted:"+request.isAsyncStarted());
		System.out.println("AsyncSupported:"+request.isAsyncSupported());
		System.out.println("RequestedSessionIdFromCookie:"+request.isRequestedSessionIdFromCookie());
		System.out.println("RequestedSessionIdFromURL:"+request.isRequestedSessionIdFromURL());
		System.out.println("RequestedSessionIdValid:"+request.isRequestedSessionIdValid());
		System.out.println("Secure:"+request.isSecure());
		System.out.println("DispatcherType:"+request.getDispatcherType());
		System.out.println("----------------------------------------------------");
		System.out.println("Locale:");
		Enumeration<Locale> locales=request.getLocales();
		while(locales.hasMoreElements()){
			System.out.println("\t"+locales.nextElement());
		}
		System.out.println("----------------------------------------------------");
		System.out.println("RequestURL:"+request.getRequestURL());
		System.out.println("----------------------------------------------------");
		System.out.println("request header:");
		
		Enumeration<String> headerNames=request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String headerName=headerNames.nextElement();
			Enumeration<String> headerValues=request.getHeaders(headerName);
			while(headerValues.hasMoreElements()){
				String headerValue=headerValues.nextElement();
				System.out.println("\t"+headerName+"="+headerValue);
			}
		}
		System.out.println("----------------------------------------------------");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("----------------------------------------------------");
		System.out.println("postHandle:");
		System.out.println("\thandler name:"+handler.getClass().getName());
		String viewName=null;
		if(modelAndView!=null){
			viewName=modelAndView.getViewName();
		}
		System.out.println("\tmodelAndView:"+viewName);
		System.out.println("----------------------------------------------------");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("----------------------------------------------------");
		System.out.println("response info:");
		System.out.println("BufferSize:"+response.getBufferSize());
		System.out.println("CharacterEncoding:"+response.getCharacterEncoding());
		System.out.println("ContentType:"+response.getContentType());
		System.out.println("----------------------------------------------------");
		System.out.println("response headers:");
		Collection<String> responseHeaderNames=response.getHeaderNames();
		for(String headerName:responseHeaderNames){
			Collection<String> responseHeaderValues=response.getHeaders(headerName);
			for(String responseHeaderValue:responseHeaderValues){
				System.out.println("\t"+headerName+":"+responseHeaderValue);
			}
		}
		System.out.println("----------------------------------------------------");
		System.out.println("Locale:"+response.getLocale());
		System.out.println("Status:"+response.getStatus());
		System.out.println("Committed:"+response.isCommitted());
		System.out.println("----------------------------------------------------");
	}

}
