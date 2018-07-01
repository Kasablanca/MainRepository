package com.min.springbootdemo.service;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.springbootdemo.dao.mapper.AuthorityMapper;
import com.min.springbootdemo.dao.model.Authority;
import com.min.springbootdemo.dto.Result;
import com.min.springbootdemo.dto.ZTreeNode;

@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityMapper authorityMapper;
	
	public Result getDescendant(Integer parentId) {
		Result result = Result.newSuccessInstance();
		List<ZTreeNode> allNodes = new LinkedList<>(authorityMapper.getAllNodes());
		List<ZTreeNode> children = new LinkedList<>();
		
		Iterator<ZTreeNode> it = allNodes.iterator();
		if(parentId==null) {
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
	
	private void setChildren(List<ZTreeNode> children,List<ZTreeNode> allNodes) {
		for(ZTreeNode child:children) {
			Iterator<ZTreeNode> it = allNodes.iterator();
			while(it.hasNext()) {
				ZTreeNode item = it.next();
				if(child.getId().equals(item.getParentId())) {
					child.getChildren().add(item);
					it.remove();
				}
			}
			setChildren(child.getChildren(),allNodes);
		}
	}
	
	public Authority getById(Integer id) {
		return authorityMapper.selectByPrimaryKey(id);
	}

	public Result delete(Authority target) {
		Result result = Result.newErrorInstance();
		
		Authority temp = authorityMapper.selectByPrimaryKey(target.getId());
		if(temp == null) {
			result.setMessage("目标资源不存在或已被删除");
			return result;
		}
		if(!temp.getVerNo().equals(target.getVerNo())) {
			result.setMessage("版本号错误，请重新刷新页面");
			return result;
		}
		
		result.setData(authorityMapper.deleteByPrimaryKey(target.getId()));
		return result;
	}

	public Result insert(Authority target) {
		Result result = Result.newErrorInstance();
		
		if(target.getParentId() != null) {
			Authority parent = authorityMapper.selectByPrimaryKey(target.getParentId());
			if(parent == null) {
				result.setMessage("上级权限不存在");
				return result;
			}
			if(!parent.getType().equals((byte)0) && !parent.getType().equals((byte)1)) {
				result.setMessage("上级权限类型无效");
			}
		}
		
		Date now = new Date();
		
		target.setAddTime(now);
		target.setUpdTime(now);
		target.setStatus((byte)0);
		target.setType(target.getType());
		target.setVerNo(1);
		
		result = Result.newSuccessInstance();
		result.setData(authorityMapper.insert(target));
		return result;
	}

	public Result update(Authority target) {
		Result result = Result.newErrorInstance();
		
		if(target.getParentId() != null) {
			Authority parent = authorityMapper.selectByPrimaryKey(target.getParentId());
			if(parent == null) {
				result.setMessage("上级权限不存在或已被删除");
				return result;
			}
			if(!parent.getType().equals((byte)0) && !parent.getType().equals((byte)1)) {
				result.setMessage("上级权限类型无效");
				return result;
			}
		}
		
		Authority temp = authorityMapper.selectByPrimaryKey(target.getId());
		if(temp == null) {
			result.setMessage("权限不存在或已被删除");
			return result;
		}
		if(!temp.getVerNo().equals(target.getVerNo())) {
			result.setMessage("版本号错误，请重新刷新页面");
			return result;
		}
		
		target.setUpdTime(new Date());
		target.setVerNo(target.getVerNo()+1);
		
		result = Result.newSuccessInstance();
		result.setData(authorityMapper.updateByPrimaryKeySelective(target));
		return result;
	}

}
