package com.min.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.min.springbootdemo.dao.mapper.UserMapper;
import com.min.springbootdemo.dao.model.User;

@Service
@CacheConfig(cacheNames="user")
public class UserService {

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
	
}
