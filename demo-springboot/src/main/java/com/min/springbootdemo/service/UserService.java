package com.min.springbootdemo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.min.springbootdemo.dao.mapper.UserMapper;
import com.min.springbootdemo.dao.model.User;

@Service
@CacheConfig(cacheNames="user")
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Cacheable
	public User findById(Integer id) {
		System.out.println(Thread.currentThread().getId());
		return userMapper.selectByPrimaryKey(id);
	}
	
	@CacheEvict
	public int delete(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}
	
	@CachePut(key="#user.id")
	public User add(User user) {
		userMapper.insert(user);
		return user;
	}
	
	@PostConstruct
	public void init() {
		initTask();
		System.out.println("thread id: "+Thread.currentThread().getId());
	}
	
	@Async
	public void initTask() {
		task();
	}
	
	@Scheduled(cron="1/10 * * * * ?")
	public void scheduledTask() {
		task();
	}
	
	@Transactional
	public void task() {
		System.out.println("thread id: "+Thread.currentThread().getId());
		System.out.println("isNewTransaction: "+TransactionAspectSupport.currentTransactionStatus().isNewTransaction());
	}
	
}
