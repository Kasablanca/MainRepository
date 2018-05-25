package com.syhd.ahriman.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syhd.ahriman.dao.mapper.UserMapper;
import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.dto.Pagination;
import com.syhd.ahriman.dto.Sort;
import com.syhd.ahriman.dto.TableData;

@Service
@CacheConfig(cacheNames="user")
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Cacheable
	public User findById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Cacheable
	public TableData findAll(Pagination pagination, Sort sort){
		return new TableData(userMapper.findAllCount(),userMapper.findAll(pagination,sort));
	}

	@CachePut(key="#user.id")
	@Transactional
	public User insert(User user) {
		userMapper.insert(user);
		return user;
	}

	@Cacheable
	public List<Map<?, ?>> userDistribution(){
		return userMapper.userDistribution();
	}
	
	public User insert2(User user) {
		return insert(user);
	}

}
