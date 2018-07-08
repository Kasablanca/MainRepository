package com.syhd.ahriman.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 游戏服务器配置POJO
 * @author MIN.LEE
 *
 */
@Component
@ConfigurationProperties(prefix = "gamelog")
public class GamelogProperties {
	
	public static final int DEFAULT_PORT = 3306;
	public static final String DEFAULT_PREFIX = "jdbc:mysql:";
	
	/**游戏数据库URL前缀，默认为"jdbc:mysql:"*/
	private String prefix;
	
	/**游戏数据库用户名*/
	private String username;
	
	/**游戏数据库密码*/
	private String password;
	
	/**游戏数据库端口，默认为3306*/
	private Integer port;
	
	public String getPrefix() {
		return prefix == null ? DEFAULT_PREFIX : prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getPort() {
		return port == null ? DEFAULT_PORT : port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

}
