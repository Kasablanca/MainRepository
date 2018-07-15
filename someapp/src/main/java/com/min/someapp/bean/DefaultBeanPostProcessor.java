package com.min.someapp.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

//@Component
public class DefaultBeanPostProcessor implements BeanPostProcessor,Ordered {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanName:"+beanName);
		System.out.println(bean.getClass().getSimpleName());
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//System.out.println("beanName:"+beanName);
		//System.out.println(bean.getClass().getSimpleName());
		return bean;
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
