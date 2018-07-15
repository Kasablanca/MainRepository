package com.min.someapp.bean;

import org.springframework.beans.factory.FactoryBean;

//@Component
public class FactoryBeanDemo implements FactoryBean<Object> {
	private Object account;

	@Override
	public Object getObject() throws Exception {
		if(account == null){
			account = new Object();
		}
		return account;
	}

	@Override
	public Class<?> getObjectType() {
		return Object.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
