package com.min.someapp.utils;

import javax.servlet.http.HttpSession;

import com.min.someapp.dao.model.User;
import com.min.someapp.web.security.AuthorityInterceptor;

public class HttpUtils {
	
	public static User getUserInSession(HttpSession httpSession) {
		return (User) httpSession.getAttribute(AuthorityInterceptor.USER_IN_SESSION);
	}
	
	public static Integer getUserIdInSession(HttpSession httpSession) {
		return ((User) httpSession.getAttribute(AuthorityInterceptor.USER_IN_SESSION)).getId();
	}

}
