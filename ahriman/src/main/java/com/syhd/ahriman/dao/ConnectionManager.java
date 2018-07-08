package com.syhd.ahriman.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.service.impl.AppServerService;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnectionManager {
	
	private ConcurrentHashMap<AppServer,HikariDataSource> map = new ConcurrentHashMap<>();
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	/**
	 * Attempts to establish a connection with the data source that this DataSource object represents. 
	 * @param server database information
	 * @return a connection to the data source
	 * @throws SQLException if a database access error occurs
	 */
	public Connection getConnection(AppServer server) throws SQLException {
		if(map.containsKey(server)) {
			return map.get(server).getConnection();
		} else {
			HikariDataSource dataSource = new HikariDataSource();
			dataSource.setIdleTimeout(1800000);//30分钟
			dataSource.setUsername(gamelogProperties.getUsername());
			dataSource.setPassword(gamelogProperties.getPassword());
			dataSource.setJdbcUrl(appServerService.getLogDbUrl(server.getLogDb()));
			map.put(server, dataSource);
			return dataSource.getConnection();
		}
	}

}
