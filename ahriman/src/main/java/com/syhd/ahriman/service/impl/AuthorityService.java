package com.syhd.ahriman.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.AuthorityMapper;
import com.syhd.ahriman.dao.model.Authority;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.ZTreeNode;

@Service
@CacheConfig(cacheNames="authority")
public class AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Cacheable(key="#root.methodName+#parentId")
	public Result getDescendant(Integer parentId) {
		Result result = Result.getSuccessResult();
		List<ZTreeNode> allNodes = new LinkedList<>(authorityMapper.getAllNodes());
		List<ZTreeNode> children = new LinkedList<>();
		Iterator<ZTreeNode> it = allNodes.iterator();
		if(parentId == null) {
			// 查询所有的根节点
			while(it.hasNext()) {
				ZTreeNode item = it.next();
				if(item.getParentId()==null) {
					children.add(item);
					it.remove();
				}
			}
		} else {
			while(it.hasNext()) {
				ZTreeNode item = it.next();
				if(parentId.equals(item.getParentId())) {
					children.add(item);
					it.remove();
				}
			}
		}
		
		if(children.size()>0) {
			setChildren(children,allNodes);
		}
		result.setData(children);
		return result;
	}
	
	/**
	 * 递归设置子节点
	 * @param children 待设置的节点集合
	 * @param allNodes 所有节点
	 */
	private void setChildren(List<ZTreeNode> children,List<ZTreeNode> allNodes) {
		for(ZTreeNode child : children) {
			Integer parentId = child.getId();
			Iterator<ZTreeNode> it = allNodes.iterator();
			while(it.hasNext()) {
				ZTreeNode item = it.next();
				if(parentId.equals(item.getParentId())) {
					child.getChildren().add(item);
					it.remove();
				}
			}
			setChildren(child.getChildren(), allNodes);
		}
	}
	
	@Caching(evict= {
			@CacheEvict(cacheNames="authority",allEntries=true),
			@CacheEvict(cacheNames="authorityGroups",allEntries=true),
			@CacheEvict(cacheNames="user",allEntries=true)})
	public Result insert(Authority target) {
		Result result = Result.getErrorResult();
		
		if(target.getParentId() != null) {
			Authority parent = authorityMapper.selectByPrimaryKey(target.getParentId());
			if(parent == null) {
				result.setMessage("父权限不存在，或已经被删除，请刷新页面后重试");
				return result;
			}
			byte parentType = parent.getType();
			if(!Authority.Type.canBeParent(parentType)) {
				result.setMessage("父权限类型无效");
				return result;
			}
		}
		
		int nameConflict = authorityMapper.selectCountByName(target.getName(),target.getParentId(),target.getId());
		if(nameConflict > 0) {
			result.setMessage("权限名已经存在");
			return result;
		}
		
		Date now = new Date();
		
		target.setAddTime(now);
		target.setStatus((byte)0);
		target.setUpdTime(now);
		target.setVerNo(1);
		
		result = Result.getSuccessResult();
		result.setData(authorityMapper.insert(target));
		return result;
	}

	@Caching(evict= {
			@CacheEvict(cacheNames="authority",allEntries=true),
			@CacheEvict(cacheNames="authorityGroups",allEntries=true),
			@CacheEvict(cacheNames="user",allEntries=true)})
	public Result update(Authority target) {
		Result result = Result.getErrorResult();
		
		if(target.getParentId() != null) {
			Authority parent = authorityMapper.selectByPrimaryKey(target.getParentId());
			if(parent == null) {
				result.setMessage("父权限不存在，或已经被删除，请刷新页面后重试");
				return result;
			}
			byte parentType = parent.getType();
			if(!Authority.Type.canBeParent(parentType)) {
				result.setMessage("父权限类型无效");
				return result;
			}
		}
		
		int nameConflict = authorityMapper.selectCountByName(target.getName(),target.getParentId(),target.getId());
		if(nameConflict > 0) {
			result.setMessage("权限名已经存在");
			return result;
		}
		
		Authority temp = authorityMapper.selectByPrimaryKey(target.getId());
		if(temp == null) {
			result.setMessage("权限不存在，或已经被删除，请刷新页面后重试");
			return result;
		} else {
			if(!temp.getVerNo().equals(target.getVerNo())) {
				result.setMessage("版本号错误，请刷新页面后重试");
				return result;
			}
		}
		
		Date now = new Date();
		
		target.setUpdTime(now);
		target.setVerNo(target.getVerNo()+1);
		
		result = Result.getSuccessResult();
		result.setData(authorityMapper.updateByPrimaryKeySelective(target));
		return result;
	}
	
	@Caching(evict= {
			@CacheEvict(cacheNames="authority",allEntries=true),
			@CacheEvict(cacheNames="authorityGroups",allEntries=true),
			@CacheEvict(cacheNames="user",allEntries=true)})
	public Result delete(Authority target) {
		Result result = Result.getErrorResult();
		
		Authority temp = authorityMapper.selectByPrimaryKey(target.getId());
		if(temp == null) {
			result.setMessage("权限不存在，或已经被删除，请刷新页面后重试");
			return result;
		} else {
			if(!temp.getVerNo().equals(target.getVerNo())) {
				result.setMessage("版本号错误，请刷新页面后重试");
				return result;
			}
		}
		
		result = Result.getSuccessResult();
		result.setData(authorityMapper.deleteByPrimaryKey(target.getId()));
		return result;
	}

	@Cacheable(key="#root.methodName+#id")
	public Authority findById(Integer id) {
		return authorityMapper.selectByPrimaryKey(id);
	}
	
}
