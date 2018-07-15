package com.min.someapp.service.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.someapp.dao.mapper.AuthorityGroupMapper;
import com.min.someapp.dao.model.AuthorityGroup;
import com.min.someapp.dao.model.GroupAuthority;
import com.min.someapp.dto.PageAndSort;
import com.min.someapp.dto.Result;
import com.min.someapp.dto.TableData;

@Service
@CacheConfig(cacheNames="authorityGroups")
public class AuthorityGroupService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private AuthorityGroupMapper authorityGroupMapper;
	
	@Autowired
	private ObjectMapper jackson;
	
	public AuthorityGroup getById(Integer id) {
		return authorityGroupMapper.selectByPrimaryKey(id);
	}
	
	@Caching(evict= {
			@CacheEvict(cacheNames="authorityGroups",allEntries=true),
			@CacheEvict(cacheNames="user",allEntries=true)})
	@Transactional
	public Result insert(AuthorityGroup record,String authorityIds) {
		Result result = Result.getErrorResult();
		
		AuthorityGroup temp = authorityGroupMapper.getByGroupName(record.getGroupName());
		if(temp != null) {
			result.setMessage("该角色名已存在");
			return result;
		}
		
		Date now = new Date();
		
		record.setVerNo(1);
		record.setAddTime(now);
		record.setUpdTime(now);
		record.setStatus((byte) 0);
		authorityGroupMapper.insert(record);
		
		if(!StringUtils.isEmpty(authorityIds)) {
			try {
				List<Integer> ids = jackson.readValue(authorityIds, new TypeReference<List<Integer>>() {});
				List<GroupAuthority> list = new ArrayList<>(ids.size());
				Integer groupId = record.getId();
				for(Integer id : ids) {
					list.add(new GroupAuthority(groupId, id));
				}
				
				if(list.size()>0) {
					authorityGroupMapper.batchInsertAuthorities(list);
				}
			} catch (IOException e) {
				logger.trace("反序列化失败", e.getCause());
				result.setMessage("操作失败");
				return result;
			}
		}
		
		return Result.getSuccessResult();
	}
	
	public TableData getRecordList(AuthorityGroup filter,PageAndSort pageAndSort) {
		List<AuthorityGroup> userList = authorityGroupMapper.getRecordList(filter,pageAndSort);
		long count = authorityGroupMapper.getRecordListCount(filter,pageAndSort);
		
		TableData result = new TableData();
		result.setCode(0);
		result.setCount(count);
		result.setData(userList);
		
		return result;
	}
	
	@Caching(evict= {
			@CacheEvict(cacheNames="authorityGroups",allEntries=true),
			@CacheEvict(cacheNames="user",allEntries=true)})
	@Transactional
	public Result delete(AuthorityGroup role) {
		Result result = Result.getErrorResult();
		
		AuthorityGroup temp = authorityGroupMapper.selectByPrimaryKey(role.getId());
		if(temp == null) {
			result.setMessage("不存在此角色");
			return result;
		} else {
			if(!temp.getVerNo().equals(role.getVerNo())) {
				result.setMessage("版本号不正确，请刷新页面后重试");
				return result;
			}
		}
		
		authorityGroupMapper.deleteByPrimaryKey(role.getId());
		authorityGroupMapper.clearAuthorities(role.getId());
		return Result.getSuccessResult();
	}
	
	@Caching(evict= {
			@CacheEvict(cacheNames="authorityGroups",allEntries=true),
			@CacheEvict(cacheNames="user",allEntries=true)})
	@Transactional
	public Result update(AuthorityGroup role,String authorityIds) {
		Result result = Result.getErrorResult();
		
		AuthorityGroup temp = authorityGroupMapper.selectByPrimaryKey(role.getId());
		AuthorityGroup temp2 = authorityGroupMapper.getByGroupName(role.getGroupName());
		if(temp == null) {
			result.setMessage("不存在此角色");
			return result;
		} else {
			if(temp2 != null && !temp.getId().equals(temp2.getId())) {
				result.setMessage("角色名重复");
				return result;
			}
			if(!temp.getVerNo().equals(role.getVerNo())) {
				result.setMessage("版本号不正确，请刷新页面后重试");
				return result;
			}
		}
		
		Date now = new Date();
		
		role.setAddTime(now);
		role.setUpdTime(now);
		role.setVerNo(temp.getVerNo()+1);
		authorityGroupMapper.updateByPrimaryKeySelective(role);
		
		if(!StringUtils.isEmpty(authorityIds)) {
			try {
				List<Integer> ids = jackson.readValue(authorityIds, new TypeReference<List<Integer>>() {});
				List<GroupAuthority> list = new ArrayList<>(ids.size());
				Integer groupId = role.getId();
				for(Integer id : ids) {
					list.add(new GroupAuthority(groupId, id));
				}
				
				authorityGroupMapper.clearAuthorities(groupId);
				if(list.size()>0) {
					authorityGroupMapper.batchInsertAuthorities(list);
				}
			} catch (IOException e) {
				logger.trace("反序列化失败", e.getCause());
				result.setMessage("操作失败");
				return result;
			}
		}
		
		return Result.getSuccessResult();
	}
	
	/**
	 * 获取角色所具有的权限
	 * @param groupId 角色ID
	 * @return 权限标识集合
	 */
	public List<Integer> getAuthoritiesId(Integer groupId){
		return authorityGroupMapper.getAuthorities(groupId);
	}
	
	/**
	 * 获取角色所具有的权限
	 * @param groupId 角色ID
	 * @return 权限URL集合
	 */
	public List<String> getAuthoritiesUri(Integer groupId) {
		return authorityGroupMapper.getAuthoritiesUrl(groupId);
	}
	
	/**
	 * 获取所有角色
	 * @return 包含角色ID和角色名的集合
	 */
	public List<AuthorityGroup> getAllRecord() {
		return authorityGroupMapper.getAllRecord();
	}

}
