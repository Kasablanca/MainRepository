package com.min.dao.entity;

import javax.persistence.Entity;

@Entity
public class User {

    private String userId;

    private String userNick;

    private Byte userSex;
    
	public User() {
		super();
	}

	public User(String userId, String userNick, Byte userSex) {
		super();
		this.userId = userId;
		this.userNick = userNick;
		this.userSex = userSex;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public Byte getUserSex() {
		return userSex;
	}

	public void setUserSex(Byte userSex) {
		this.userSex = userSex;
	}

}