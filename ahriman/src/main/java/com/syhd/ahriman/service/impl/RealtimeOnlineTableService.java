package com.syhd.ahriman.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.syhd.ahriman.dao.mapper.OnlineTableMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.OnlineTable;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.StatisticPayload;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.service.CronTask;
import com.syhd.ahriman.utils.DateUtils;

//@Service
@CacheConfig(cacheNames="realTimeOnlineTable")
public class RealtimeOnlineTableService {

	private static final Logger logger = LoggerFactory.getLogger(RealtimeOnlineTableService.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private OnlineTableMapper onlineTableMapper;
	
	/**
	 * 获取实时在线图数据
	 * @param param 查询参数
	 * @param pageAndSort 分页参数
	 * @return 包含汇总数据
	 */
	public Result getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,0,false);
		
		List<OnlineTable> list = onlineTableMapper.getStatistic(copy,pageAndSort);
		
		Result result = Result.getSuccessResult();
		StatisticPayload data = new StatisticPayload(list, RequestPayload.unPrepare(copy));
		result.setData(data);
		
		return result;
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return onlineTableMapper.getAllPlatform();
	}
	
/*====================================分割线,以下方法非对外使用====================================*/
	
	@CronTask("0 0 * * * ?") //每小时执行一次,并且项目启动时也会执行一次
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			internalTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的实时在线表数据
	 * @param server 游戏日志服务器信息
	 */
	public void internalTask(AppServer server) {
		Date startDate = onlineTableMapper.getLastCountDate(server.getServerid());
		if(startDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(startDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		Date endDate = DateUtils.getTommorowTime0();
		
		doInternalTask(startDate, endDate, server, "t_online_table");
	}
	
	private void doInternalTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			CallableStatement stmt = null;
			if(startDate.before(endDate)) {
				stmt = conn.prepareCall("call proc_get_realtime_online_table(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setTimestamp(1, DateUtils.conver2SqlTimestamp(startDate));
				stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
				
				ResultSet resultSet = stmt.executeQuery();
				final int batchSize = 100; // 每次批量插入的阈值
				List<OnlineTable> recordList = new ArrayList<>(batchSize);
				while(resultSet.next()) {
					OnlineTable record = resultMap(resultSet);
					record.setServerId(server.getServerid());
					
					recordList.add(record);
					
					if(recordList.size() == batchSize) {
						onlineTableMapper.batchInsert(recordList,storedTable);
						recordList.clear();
					}
				}
				if(recordList.size() > 0) {
					onlineTableMapper.batchInsert(recordList,storedTable);
				}
			}
			
		} catch (Exception e) {
			logger.error("实时在线表数据查询失败", e.getCause());
			throw new RuntimeException("实时在线表数据查询失败"); // 需要回滚事务
		}
	}
	
	private OnlineTable resultMap(ResultSet resultSet) throws SQLException {
		Date date = resultSet.getDate(1);
		String platform = resultSet.getString(2);
		Timestamp time = resultSet.getTimestamp(3);
		Integer liveCount = resultSet.getInt(4);

		OnlineTable result = new OnlineTable();
		result.setDate(date);
		result.setPlatform(platform);
		result.setLiveCount(liveCount);
		
		if(DateUtils.isIntegralPoint(time)) {
			Calendar now = Calendar.getInstance();
			now.setTime(time);
			int hour = now.get(Calendar.HOUR_OF_DAY);
			switch(hour) {
			case 0:
				result.setH0(liveCount);
			case 1:
				result.setH1(liveCount);
			case 2:
				result.setH2(liveCount);
			case 3:
				result.setH3(liveCount);
			case 4:
				result.setH4(liveCount);
			case 5:
				result.setH5(liveCount);
			case 6:
				result.setH6(liveCount);
			case 7:
				result.setH7(liveCount);
			case 8:
				result.setH8(liveCount);
			case 9:
				result.setH9(liveCount);
			case 10:
				result.setH10(liveCount);
			case 11:
				result.setH11(liveCount);
			case 12:
				result.setH12(liveCount);
			case 13:
				result.setH13(liveCount);
			case 14:
				result.setH14(liveCount);
			case 15:
				result.setH15(liveCount);
			case 16:
				result.setH16(liveCount);
			case 17:
				result.setH17(liveCount);
			case 18:
				result.setH18(liveCount);
			case 19:
				result.setH19(liveCount);
			case 20:
				result.setH20(liveCount);
			case 21:
				result.setH21(liveCount);
			case 22:
				result.setH22(liveCount);
			case 23:
				result.setH23(liveCount);
			default:
				//throw new IllegalArgumentException("无效的时间：");
			}
		}
		
		return result;
	}
	
}
