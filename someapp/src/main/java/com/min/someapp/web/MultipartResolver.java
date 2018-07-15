package com.min.someapp.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MultipartResolver extends CommonsMultipartResolver {
	
	@Override
	public boolean isMultipart(HttpServletRequest request) {
		return request != null && FileUploadBase.isMultipartContent(new ServletRequestContext(request));
	}
	
}
