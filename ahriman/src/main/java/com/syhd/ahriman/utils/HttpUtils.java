package com.syhd.ahriman.utils;

import javax.servlet.http.HttpSession;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.web.security.AuthorityInterceptor;

public class HttpUtils {
	
	public static User getUserInSession(HttpSession httpSession) {
		return (User) httpSession.getAttribute(AuthorityInterceptor.USER_IN_SESSION);
	}
	
	public static Integer getUserIdInSession(HttpSession httpSession) {
		return ((User) httpSession.getAttribute(AuthorityInterceptor.USER_IN_SESSION)).getId();
	}

}
