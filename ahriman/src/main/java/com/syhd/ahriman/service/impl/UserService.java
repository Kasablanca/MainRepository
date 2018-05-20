package com.syhd.ahriman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syhd.ahriman.dao.mapper.UserMapper;
import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.web.Pagination;
import com.syhd.ahriman.web.Sort;
import com.syhd.ahriman.web.TableData;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User findById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
	
	public TableData findAll(Pagination pagination, Sort sort){
		return new TableData(userMapper.findAllCount(),userMapper.findAll(pagination,sort));
	}
	
	public int insert(User user) {
		userMapper.insert(user);
		if(user.getAge()<1) {
			throw new RuntimeException("无效的年龄");
		}
		user.setId(user.getId()+1);
		return userMapper.insertSelective(user);
	}
	
}
