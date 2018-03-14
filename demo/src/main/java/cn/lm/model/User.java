package cn.lm.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class User {
	private String userId;
	private String username;
	private String password;
	
	// 前台图片字符串
	private String imageDataString;

	public User() {
		super();
	}
	public User(String userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getImageDataString() {
		return imageDataString;
	}
	public void setImageDataString(String imageDataString) {
		this.imageDataString = imageDataString;
	}
	
}
