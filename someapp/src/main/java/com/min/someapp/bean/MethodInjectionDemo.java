package com.min.someapp.bean;

import org.springframework.beans.factory.annotation.Lookup;

import com.min.someapp.dao.model.User;

//@Component
public abstract class MethodInjectionDemo {
	private User user;
	
	public void printUsername(){
		user =createUser();
		System.out.println(user.getUsername());
	}
	
	@Lookup
	protected abstract User createUser();
	
	
}
