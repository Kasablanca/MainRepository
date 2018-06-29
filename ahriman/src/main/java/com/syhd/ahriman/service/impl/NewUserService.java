package com.syhd.ahriman.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.DailyNewRoleMapper;
import com.syhd.ahriman.dao.mapper.UserInfoMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.DailyNewRole;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.StatisticPayload;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.service.CronTask;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="newuser")
public class NewUserService {
	
	private static Logger logger = LoggerFactory.getLogger(NewUserService.class);
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private DailyNewRoleMapper dailyNewRoleMapper;
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	/**
	 * 获取KPI新增用户数据
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	public Result getStatistic(RequestPayload param) {
		RequestPayload copy = RequestPayload.prepare(param,null,null);
		
		Result result = Result.getSuccessResult();
		Map<String,Object> response = new HashMap<>();
		
		if(copy.getServerId() == null || copy.getServerId().length == 0) {
			//没筛选服务器时才显示新增账号
			response.put("newAccount", KpiStatistic.fill(userInfoMapper.getStatistic(copy), copy.getStart(), copy.getEnd()));
		}
		
		if(RequestPayload.containToday(copy)) {
			//查询期限包含今天
			doRealtimeStatistic();
			response.put("newRole", KpiStatistic.fill(dailyNewRoleMapper.getMixinStatistic(copy), copy.getStart(), copy.getEnd()));
		} else {
			//查询期限不包含今天
			response.put("newRole", KpiStatistic.fill(dailyNewRoleMapper.getStatistic(copy), copy.getStart(), copy.getEnd()));
		}
		
		StatisticPayload data = new StatisticPayload(response, RequestPayload.unPrepare(copy));
		result.setData(data);
		return result;
	}
	
	/**
	 * 查询实时数据，并放进临时表
	 */
	private void doRealtimeStatistic() {
		List<AppServer> serverList = appServerService.getAllServer();
		
		Date startDate = DateUtils.getTodayTime0();
		Calendar now = Calendar.getInstance();
		now.setTime(startDate);
		now.add(Calendar.DAY_OF_MONTH, 1);
		Date endDate = now.getTime();
		
		dailyNewRoleMapper.createTodayNewRoleTable();
		for(AppServer server : serverList) {
			doNewUserTask(startDate,endDate,server,"t_today_new_role");
		}
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return dailyNewRoleMapper.getAllPlatform();
	}
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	@CronTask("0 0 0 * * ?")
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			newUserTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的新增用户/角色数据信息
	 * @param server 游戏日志服务器信息
	 */
	public void newUserTask(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		Date lastCountDate = dailyNewRoleMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doNewUserTask(startDate, endDate, server, "t_daily_new_role");
	}
	
	private void doNewUserTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			PreparedStatement stmt = conn.prepareStatement(
					"select date,platform,count(pid) count from " + 
					"(select date(date) date,platform,pid from t_createplayerlog where date >= ? and date < ?) t "+
					"group by date,platform");
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			ResultSet resultSet = stmt.executeQuery();
			resultSet.setFetchSize(100);
			final int batchSize = 100; // 每次批量插入的阈值
			List<DailyNewRole> recordList = new ArrayList<>(batchSize*2);
			while(resultSet.next()) {
				Date date = resultSet.getDate(1);
				String platform = resultSet.getString(2);
				Integer count = resultSet.getInt(3);

				DailyNewRole record = new DailyNewRole();
				record.setDate(date);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setServerName(server.getServername());
				record.setCount(count);
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					dailyNewRoleMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				dailyNewRoleMapper.batchInsert(recordList,storedTable);
			}
		} catch (Exception e) {
			logger.error("日新增用户数据查询失败", e.getCause());
			throw new RuntimeException("日新增用户数据查询失败"); // 需要回滚事务
		}
	}
	
}
