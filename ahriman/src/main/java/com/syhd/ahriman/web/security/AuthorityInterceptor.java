package com.syhd.ahriman.web.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.properties.SecurityProperties;
import com.syhd.ahriman.service.impl.UserService;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {
	
	private static Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

	public static final String USER_IN_SESSION = "user_in_session";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@PostConstruct
	private void init() {
		User root = userService.getByAccName(securityProperties.getRootName());
		if(root == null) {
			// 启动项目时检查数据库是否有ROOT用户，若没ROOT用户，则使用配置文件生成默认的ROOT用户
			root = new User();
<<<<<<< HEAD
=======
			root.setAddAccId(-1);
			root.setUpdAccId(-1);
>>>>>>> 47bcc96d421f6177ed8e91b9b8e74a982907a6c4
			root.setAccName(securityProperties.getRootName());
			root.setUsername(securityProperties.getRootName());
			root.setPassword(securityProperties.getRootPassword());
			userService.insert(root,null);
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI().substring(contextPath.length());
		String requestMethod = request.getMethod();
		
		String requestSuffix = getSuffix(requestURI);
		if(contains(STATIC_SUFFIX,requestSuffix) || "/error".equals(requestURI) || "/errorPage".equals(requestURI)) {
			// 静态资源放行
			return true;
		}
		
		if(requestURI.startsWith("/login") && "GET".equalsIgnoreCase(requestMethod)) {
			// 前往登录页面
			
			if(request.getSession(true).getAttribute(USER_IN_SESSION) != null) {
				// 已经登录，跳转首页
				String originRequestUri = request.getHeader("Referer");
				if(!StringUtils.isEmpty(originRequestUri)) {
					sendRedirect(response,originRequestUri);
				} else {
					sendRedirect(response,contextPath+"/home");
				}
				return false;
			} else {
				return true;
			}
		} else if(requestURI.startsWith("/login") && "POST".equalsIgnoreCase(requestMethod)) {
			// 请求认证登录信息
			String accName = request.getParameter("accName");
			String password = request.getParameter("password");
			
			User user = userService.getByAccName(accName);
			if((user != null && StringUtils.isEmpty(user.getPassword()) && StringUtils.isEmpty(password)) ||
					( user != null && !StringUtils.isEmpty(user.getPassword()) && user.getPassword().equals(password)) ) {
				// 密码通过
				// 更新用户上次登录时间
				user.setLastLoginTime(new Date());
				userService.updateLastLoginTime(user);
				
				request.getSession(true).setAttribute(USER_IN_SESSION, user);
				sendRedirect(response,contextPath+"/home");
			} else {
				// 密码没通过
				sendRedirect(response,contextPath+"/login?error=true");
			}
			
			return false;
		} else if(requestURI.startsWith("/logout")) {
			request.getSession(true).invalidate();
			sendRedirect(response,contextPath+"/login");
			return false;
		} else {
			HttpSession session = request.getSession();
			if(session == null || session.getAttribute(USER_IN_SESSION) == null) {
				// 没认证，跳转登录页面
				sendRedirect(response,contextPath+"/login");
				return false;
			} else {
				// 已经认证，判断是否有权限
				return doAuthorize(request,requestURI,response,(User) session.getAttribute(USER_IN_SESSION));
			}
		}
	}
	
	/**
	 * 用户授权
	 * @param request HTTP请求
	 * @param requestURI HTTP请求URI
	 * @param response HTTP响应
	 * @param target 用户信息
	 * @return true说明通过，false说明没通过
	 */
	private boolean doAuthorize(HttpServletRequest request, String requestURI, HttpServletResponse response, User target) {
		if(securityProperties.getRootName().equals(target.getAccName())) {
			// 说明是系统默认账号，通过
			return true;
		}
		
		List<String> haveAuthorities = userService.getAuthoritiesUri(target.getId());
		if(haveAuthorities.contains(requestURI)) {
			// 说明有权限
			return true;
		} else {
			// 说明没权限
			if(contains(PERMIT_URI,requestURI)) {
				// 目标资源不需要权限
				return true;
			} else {
				// 目标资源需要权限
				request.setAttribute("error", "权限不足，请联系管理员");
				forward(request,response,"/errorPage");
				return false;
			}
		}
	}
	
	/**
	 * 请求重定向
	 * @param response HTTP响应
	 * @param location 重定向URL
	 */
	private void sendRedirect(HttpServletResponse response, String location) {
		try {
			response.sendRedirect(location);
		} catch (IOException e) {
			logger.trace("请求重定向时发生IO错误", e.getCause());
			throw new IllegalStateException("请求重定向时发生IO错误");
		}
	}
	
	/**
	 * 请求转发
	 * @param request HTTP请求
	 * @param response HTTP响应
	 * @param location 转发URL
	 */
	private void forward(HttpServletRequest request,HttpServletResponse response,String location) {
		try {
			request.getRequestDispatcher(location).forward(request, response);
		} catch (ServletException | IOException e) {
			logger.trace("请求转发时发生IO错误", e.getCause());
			throw new IllegalStateException("请求重定向时发生IO错误");
		}
	}
	
	/**
	 * 判断目标是否被数组包含
	 * @param array 数组
	 * @param target 判断目标
	 * @return
	 */
	private boolean contains(String[] array,String target) {
		for(String item : array) {
			if(item.equals(target)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取请求URI的后缀
	 * @param target 请求URI
	 * @return 以点（.）开始的后缀
	 */
	private static String getSuffix(String target) {
		int indexOfDot = target.lastIndexOf(".");
		if(indexOfDot == -1) {
			return null;
		}
		return target.substring(indexOfDot);
	}
	
	public static final String[] PERMIT_URI = {"/home","/dashboard"};

	public static final String[] STATIC_SUFFIX = {".js",".css",".jpg",".gif",".png",".woff",".ttf",".svg",".eot",".map"};
}
