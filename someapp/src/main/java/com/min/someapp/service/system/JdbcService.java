package com.min.someapp.service.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.someapp.dao.ConnectionManager;
import com.min.someapp.dao.mapper.ServerMapper;
import com.min.someapp.dao.model.LoginLog;
import com.min.someapp.dao.model.Server;

@Service
public class JdbcService {

	@Autowired
	private ConnectionManager connectionManager;
	
	@Autowired
	private ServerMapper serverMapper;
	
	public List<LoginLog> getLoginLog() throws SQLException{
		List<Server> serverList = serverMapper.selectAll();
		List<LoginLog> list = new ArrayList<>();
		
		for(Server server : serverList) {
			Connection conn = connectionManager.getConnection(server.getUrl());
			PreparedStatement stmt = conn.prepareStatement("select * from t_loginlog");
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				Date loginTime = resultSet.getTimestamp("login_time");
				Integer pid = resultSet.getInt("pid");
				Integer duration = resultSet.getInt("duration");
				
				LoginLog loginLog = new LoginLog();
				loginLog.setDuration(duration);
				loginLog.setLoginTime(loginTime);
				loginLog.setPid(pid);
				loginLog.setServerId(server.getServerId());
				list.add(loginLog);
			}
		}
		
		return list;
	}
	
	public long getLoginLogCount() throws SQLException {
		List<Server> serverList = serverMapper.selectAll();
		long count = 0;
		
		for(Server server : serverList) {
			Connection conn = connectionManager.getConnection(server.getUrl());
			PreparedStatement stmt = conn.prepareStatement("select count(*) from t_loginlog");
			ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				count += resultSet.getLong(1);
			}
		}
		return count;
	}
	
}
