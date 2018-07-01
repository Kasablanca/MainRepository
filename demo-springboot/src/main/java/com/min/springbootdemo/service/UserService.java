package com.min.springbootdemo.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.min.springbootdemo.dao.mapper.UserMapper;
import com.min.springbootdemo.dao.model.User;

@Service
@CacheConfig(cacheNames="user")
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserMapper userMapper;
	
	@Cacheable
	public User findById(Integer id) {
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
	
	@Scheduled(cron="1/10 * * * * ?")
	public void scheduledTask() {
		logger.info("scheduledTask,Random = "+new Random());
	}
	
}
