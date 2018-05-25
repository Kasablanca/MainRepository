package com.syhd.ahriman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.AppServerMapper;
import com.syhd.ahriman.dao.model.AppServer;

@Service
@CacheConfig(cacheNames="appServer")
public class AppServerService {

	@Autowired
	private AppServerMapper appServerMapper;
	
	@Cacheable("#root.method")
	public List<AppServer> getAllServer(){
		return appServerMapper.getAllServer();
	}
	
}
