package com.min.someapp.dao.mapper;

import java.util.List;

import com.min.someapp.dao.model.Server;

public interface ServerMapper {
    int deleteByPrimaryKey(Integer serverId);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(Integer serverId);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);
    
    List<Server> selectAll();
}