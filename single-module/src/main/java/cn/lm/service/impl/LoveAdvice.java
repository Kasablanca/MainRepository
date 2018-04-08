package cn.lm.service.impl;

import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

import cn.lm.service.Love;

@Component
public class LoveAdvice extends DelegatingIntroductionInterceptor implements Love {

	private static final long serialVersionUID = 1L;

	@Override
	public void display(String name) {
		System.out.println("love "+name);
	}

}
