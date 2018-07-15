package com.min.someapp.bean;

import org.springframework.beans.factory.InitializingBean;

//@Component
public class InitializingBeanDemo implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean afterPropertiesSet()");
	}

}
