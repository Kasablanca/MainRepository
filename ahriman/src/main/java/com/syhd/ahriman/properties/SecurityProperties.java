package com.syhd.ahriman.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.security")
public class SecurityProperties {
	
	private static final String DEFAULT_ROOT_NAME = "admin";
	private static final String DEFAULT_ROOT_PASSWORD = "e10adc3949ba59abbe56e057f20f883e";

	/**默认root用户名, 默认admin*/
	private String rootName;
	
	/**默认root用户密码，默认123456*/
	private String rootPassword;
	
	public String getRootName() {
		return rootName == null ? DEFAULT_ROOT_NAME : rootName;
	}
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	public String getRootPassword() {
		return rootPassword == null ? DEFAULT_ROOT_PASSWORD : rootPassword;
	}
	public void setRootPassword(String rootPassword) {
		this.rootPassword = rootPassword;
	}
	
}
