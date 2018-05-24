package com.syhd.ahriman.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 汇率服务配置POJO
 * @author MIN.LEE
 *
 */
@Component
@ConfigurationProperties(prefix = "webservice.exchangerate")
public class ExchangeRateProperties {
	
	public static final String DEFAULT_PROTOCOL = "http";

	/**连接协议，如http或https*/
	private String protocol;
	
	/**连接url，如www.baidu.com*/
	private String url;
	
	/**基本的查询参数，如"name=tom&age=18"*/
	private String basequerystring;
	
	public String getProtocol() {
		return protocol == null ? DEFAULT_PROTOCOL : protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBasequerystring() {
		return basequerystring;
	}
	public void setBasequerystring(String basequerystring) {
		this.basequerystring = basequerystring;
	}
	
}
