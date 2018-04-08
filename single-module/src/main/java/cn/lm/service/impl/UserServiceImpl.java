package cn.lm.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.lm.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Override
	@Transactional
	public String sayHelloWorld() {
		System.out.println("Hello World!");
		return "Hello World!";
	}

}
