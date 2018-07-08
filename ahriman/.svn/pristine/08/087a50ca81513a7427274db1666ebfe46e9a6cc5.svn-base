package com.syhd.ahriman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.AppServerMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.properties.GamelogProperties;

@Service
@CacheConfig(cacheNames="appServer")
public class AppServerService {

	@Autowired
	private AppServerMapper appServerMapper;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	//@Cacheable("#root.method")
	public List<AppServer> getAllServer(){
		return appServerMapper.getAllServer();
	}
	
	/**
	 * 获取游戏日志服务器的URL
	 * @param logdb 原始URL值
	 * @return 有效的JDBC URL或抛出异常
	 */
	public String getLogDbUrl(String logdb) {
		try {
			int indexOfSlash = logdb.lastIndexOf("/");
			StringBuilder builder = new StringBuilder();
			builder
				.append(gamelogProperties.getPrefix())
				.append(logdb.substring(0, indexOfSlash))
				.append(":")
				.append(gamelogProperties.getPort())
				.append("/")
				.append(logdb.substring(indexOfSlash+1));
			return builder.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("无效的log_db值："+logdb);
		}
	}
	
}
