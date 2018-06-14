package com.syhd.ahriman.dao.mapper;

import java.util.List;

import com.syhd.ahriman.dao.model.AppServer;

public interface AppServerMapper {
    int deleteByPrimaryKey(Integer serverid);

    int insert(AppServer record);

    int insertSelective(AppServer record);

    AppServer selectByPrimaryKey(Integer serverid);

    int updateByPrimaryKeySelective(AppServer record);

    int updateByPrimaryKey(AppServer record);
    
    List<AppServer> getAllServer();
}