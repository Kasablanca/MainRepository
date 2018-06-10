package com.min.springbootdemo.dao.mapper;

import java.util.List;

import com.min.springbootdemo.dao.model.Authority;
import com.min.springbootdemo.dto.ZTreeNode;

public interface AuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    List<ZTreeNode> getAllNodes();
}