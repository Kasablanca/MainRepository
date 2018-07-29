package com.min.someapp.web.controller.system;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.someapp.dto.Result;
import com.min.someapp.service.system.JdbcService;

@RestController
@RequestMapping("jdbc")
public class JdbcController {
	
	@Autowired
	private JdbcService jdbcService;
	
	@RequestMapping("loginLog/list")
	public Result loginLogList() throws SQLException{
		return jdbcService.getLoginLog();
	}
	
	@RequestMapping("loginLog/count")
	public Result loginLogCount() throws SQLException{
		return jdbcService.getLoginLogCount();
	}

}
