package com.syhd.ahriman.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syhd.ahriman.dao.mapper.UserMapper;
import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.dao.model.UserGroup;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.TableData;

@Service
@CacheConfig(cacheNames="user")
public class UserService {
	
	@Autowired
	private ObjectMapper jackson;

	@Autowired
	private UserMapper userMapper;
	
	@Cacheable(key="#root.methodName+#id")
	public User getById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
	
	@Cacheable(key="#root.methodName+#username")
	public User getByAccName(String accName) {
		return userMapper.getByAccName(accName);
	}
	
	@Transactional
	@CacheEvict(allEntries=true)
	public Result insert(User target,String authorityGroupIds) {
		Result result = Result.getErrorResult();
		
		User temp = userMapper.getByAccName(target.getAccName());
		if(temp != null) {
			result.setMessage("用户名已存在");
			return result;
		}
		
		Date now = new Date();
		
		target.setVerNo(1);
		target.setAddTime(now);
		target.setUpdTime(now);
		target.setStatus((byte) 0);
		userMapper.insert(target);
		
		if(!StringUtils.isEmpty(authorityGroupIds)) {
			try {
				List<Integer> groupIds = jackson.readValue(authorityGroupIds, new TypeReference<List<Integer>>() {});
				List<UserGroup> list = new ArrayList<>(groupIds.size());
				Integer userId = target.getId();
				for(Integer groupId : groupIds) {
					list.add(new UserGroup(userId, groupId));
				}
				
				if(list.size()>0) {
					userMapper.batchInsertAuthorityGroups(list);
				}
			} catch (IOException e) {
				e.printStackTrace();
				result.setMessage("操作失败");
				return result;
			}
		}
		
		return Result.getSuccessResult();
	}
	
	@Cacheable
	public TableData getUserList(User filter,PageAndSort pageAndSort) {
		List<User> userList = userMapper.getUserList(filter,pageAndSort);
		long count = userMapper.getUserListCount(filter,pageAndSort);
		
		TableData result = new TableData();
		result.setCode(0);
		result.setCount(count);
		result.setData(userList);
		
		return result;
	}
	
	@Transactional
	@CacheEvict(allEntries=true)
	public Result delete(User user) {
		Result result = Result.getErrorResult();
		
		User temp = userMapper.selectByPrimaryKey(user.getId());
		if(temp == null) {
			result.setMessage("不存在此用户");
			return result;
		} else {
			if(!temp.getVerNo().equals(user.getVerNo())) {
				result.setMessage("版本号不正确，请刷新页面后重试");
				return result;
			}
		}
		
		userMapper.deleteByPrimaryKey(user.getId());
		userMapper.clearGroups(user.getId());
		return Result.getSuccessResult();
	}
	
	@Transactional
	@CacheEvict(allEntries=true)
	public Result update(User target,String authorityGroupIds) {
		Result result = Result.getErrorResult();
		
		User temp = userMapper.selectByPrimaryKey(target.getId());
		User temp2 = userMapper.getByAccName(target.getAccName());
		if(temp == null) {
			result.setMessage("不存在此用户");
			return result;
		} else {
			if(temp2 != null && !temp.getId().equals(temp2.getId())) {
				result.setMessage("账号重复");
				return result;
			}
			if(!temp.getVerNo().equals(target.getVerNo())) {
				result.setMessage("版本号不正确，请刷新页面后重试");
				return result;
			}
		}
		
		Date now = new Date();
		
		target.setAddTime(now);
		target.setUpdTime(now);
		target.setVerNo(temp.getVerNo()+1);
		userMapper.updateByPrimaryKeySelective(target);
		
		if(!StringUtils.isEmpty(authorityGroupIds)) {
			try {
				List<Integer> groupIds = jackson.readValue(authorityGroupIds, new TypeReference<List<Integer>>() {});
				List<UserGroup> list = new ArrayList<>(groupIds.size());
				Integer userId = target.getId();
				for(Integer groupId : groupIds) {
					list.add(new UserGroup(userId, groupId));
				}
				
				userMapper.clearGroups(userId);
				if(list.size()>0) {
					userMapper.batchInsertAuthorityGroups(list);
				}
			} catch (IOException e) {
				e.printStackTrace();
				result.setMessage("操作失败");
				return result;
			}
		}
		
		return Result.getSuccessResult();
	}
	
	@CacheEvict(allEntries=true)
	public int updateLastLoginTime(User user) {
		return userMapper.updateLastLoginTime(user);
	}
	
	/**
	 * 获取用户所具有的角色
	 * @param id 用户ID
	 * @return 角色ID集合
	 */
	@Cacheable(key="#root.methodName+#id")
	public List<Integer> getAuthorityGroupId(Integer id) {
		return userMapper.getAuthorityGroupId(id);
	}
	
	/**
	 * 获取用户所具有的权限
	 * @param id 用户ID
	 * @return 权限URI集合
	 */
	@Cacheable(key="#root.methodName+#id")
	public List<String> getAuthoritiesUri(Integer id) {
		return userMapper.getAuthoritiesUrl(id);
	}
	
}
