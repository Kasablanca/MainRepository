package cn.lm.other;

import org.springframework.beans.factory.annotation.Lookup;

import cn.lm.model.User;

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
