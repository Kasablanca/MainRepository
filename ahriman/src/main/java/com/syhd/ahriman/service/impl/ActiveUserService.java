package com.syhd.ahriman.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syhd.ahriman.dao.mapper.DailyActiveUserMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.DailyActiveUser;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.StatisticPayload;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="activeuser")
public class ActiveUserService {
	
	private static Logger logger = Logger.getLogger(ActiveUserService.class);
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private DailyActiveUserMapper dailyActiveUserMapper;
	
	@Autowired
	private AppServerService appServerService;

	/**
	 * 获取KPI活跃用户
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	public Result getDailyActiveUser(RequestPayload param) {
		RequestPayload copy = RequestPayload.prepare(param,null);
		
		Result result = Result.getSuccessResult();
		
		List<KpiStatistic> list = null;
		if(RequestPayload.containToday(copy)) {
			//查询期限包含今天
			doRealtimeStatistic();
			list = dailyActiveUserMapper.getMixinStatistic(copy);
		} else {
			//查询期限不包含今天
			list = dailyActiveUserMapper.getStatistic(copy);
		}
		
		list = KpiStatistic.fill(list, copy.getStart(), copy.getEnd());
		StatisticPayload data = new StatisticPayload(list, RequestPayload.unPrepare(copy));
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
		
		dailyActiveUserMapper.createTodayActiveuserTable();
		for(AppServer server : serverList) {
			doActiveUserTask(startDate,endDate,server,"t_today_activeuser");
		}
	}
	
	@Cacheable(key="#root.method")
	public List<DailyActiveUser> getAllServer() {
		return dailyActiveUserMapper.getAllServer();
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return dailyActiveUserMapper.getAllPlatform();
	}
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	/**
	 * 每次服务器启动时执行
	 */
	@PostConstruct
	public void init() {
		initTask();
	}
	
	/**
	 * 被异步执行，否则影响项目启动速度
	 */
	@Async
	public void initTask() {
		task();
	}
	
	/**
	 * 每天0点执行
	 */
	@Scheduled(cron="0 0 0 * * ?")
	public void dailyTask() {
		task();
	}
	
	/**
	 * 启用事务
	 */
	@Transactional
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			activeUserTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的活跃用户数据信息
	 * @param server 游戏日志服务器信息
	 */
	public void activeUserTask(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		Date lastCountDate = dailyActiveUserMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doActiveUserTask(startDate, endDate, server, "t_daily_activeuser");
	}
	
	private void doActiveUserTask(Date startDate, Date endDate, AppServer server, String storedTable) {
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
					"(select distinct date(t1.date) date,t2.platform,t1.pid from t_loginlog t1 inner join t_createplayerlog t2 on t1.pid = t2.pid where t1.date >= ? and t1.date < ? and t1.flag = 1) t "+
					"group by date,platform");
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			ResultSet resultSet = stmt.executeQuery();
			final int batchSize = 100; // 每次批量插入的阈值
			List<DailyActiveUser> recordList = new ArrayList<>(batchSize);
			while(resultSet.next()) {
				Date date = resultSet.getDate(1);
				String platform = resultSet.getString(2);
				Long count = resultSet.getLong(3);

				DailyActiveUser record = new DailyActiveUser();
				record.setDate(date);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setServerName(server.getServername());
				record.setCount(count);
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					dailyActiveUserMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				dailyActiveUserMapper.batchInsert(recordList,storedTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("活跃用户数据查询失败", e.getCause());
			throw new RuntimeException("活跃用户数据查询失败"); // 需要回滚事务
		}
	}
	
}
