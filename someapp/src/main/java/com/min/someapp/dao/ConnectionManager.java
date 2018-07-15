package com.min.someapp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnectionManager {
	
	private ConcurrentHashMap<String,HikariDataSource> map = new ConcurrentHashMap<>();
	
	@Autowired
	private Environment env;
	
	/**
	 * Attempts to establish a connection with the data source that this DataSource object represents. 
	 * @param url database url
	 * @return a connection to the data source
	 * @throws if a database access error occurs
	 */
	public Connection getConnection(String url) throws SQLException {
		try {
			if(map.containsKey(url)) {
				return map.get(url).getConnection();
			} else {
				HikariDataSource dataSource = new HikariDataSource();
				dataSource.setUsername(env.getProperty("spring.datasource.username"));
				dataSource.setPassword(env.getProperty("spring.datasource.password"));
				dataSource.setJdbcUrl(url);
				map.put(url, dataSource);
				return dataSource.getConnection();
			}
		} catch (SQLException e) {
			map.remove(url);
			throw e;
		}
	}

}
