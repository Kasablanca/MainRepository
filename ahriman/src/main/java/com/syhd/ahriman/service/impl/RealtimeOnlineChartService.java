package com.syhd.ahriman.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.OnlineCountMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.OnlineCount;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.StatisticPayload;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.service.CronTask;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="realTimeOnlineChart")
public class RealtimeOnlineChartService {

	private static final Logger logger = Logger.getLogger(RealtimeOnlineChartService.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private OnlineCountMapper onlineCountMapper;
	
	/**
	 * 获取实时在线图数据
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	public Result getStatistic(RequestPayload param) {
		RequestPayload copy = RequestPayload.prepare(param,0,false);
		
		List<OnlineCount> list = onlineCountMapper.getStatistic(copy);
		
		Result result = Result.getSuccessResult();
		StatisticPayload data = new StatisticPayload(list, RequestPayload.unPrepare(copy));
		result.setData(data);
		
		return result;
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return onlineCountMapper.getAllPlatform();
	}
	
/*====================================分割线,以下方法非对外使用====================================*/
	
	@CronTask("0 0/5 * * * ?")
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			internalTask(server);
		}
	}
	
	@CronTask("0 0 0 * * ?")
	@CacheEvict
	/**
	 * 每天定时清除一个月以前的非整点数据
	 */
	public void dailTask() {
		onlineCountMapper.cleanUp(DateUtils.getOneMonthBeforeTime0());
	}
	
	/**
	 * 抓取每个日志服务器的实时在线图数据
	 * @param server 游戏日志服务器信息
	 */
	public void internalTask(AppServer server) {
		Date startDate = onlineCountMapper.getLastCountDate(server.getServerid());
		if(startDate == null) {
			startDate = new Date(0);
		}
		Date endDate = DateUtils.getTommorowTime0();
		
		doInternalTask(startDate, endDate, server, "t_online_count");
	}
	
	private void doInternalTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			PreparedStatement stmt = conn.prepareStatement("select date,platform,person from t_onlinepersonlog where date > ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setTimestamp(1, DateUtils.conver2SqlTimestamp(startDate));
			//stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			ResultSet resultSet = stmt.executeQuery();
			final int batchSize = 100; // 每次批量插入的阈值
			List<OnlineCount> recordList = new ArrayList<>(batchSize);
			while(resultSet.next()) {
				Date date = resultSet.getTimestamp(1);
				String platform = resultSet.getString(2);
				Integer count = resultSet.getInt(3);

				OnlineCount record = new OnlineCount();
				record.setCount(count);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setTime(date);
				
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					onlineCountMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				onlineCountMapper.batchInsert(recordList,storedTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("实时在线图数据查询失败", e.getCause());
			throw new RuntimeException("实时在线图数据查询失败"); // 需要回滚事务
		}
	}
	
}
