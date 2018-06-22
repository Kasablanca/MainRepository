package com.syhd.ahriman.service.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.PlayerRemainMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.PlayerRemain;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.service.DailyTask;
import com.syhd.ahriman.utils.DateUtils;
import com.syhd.ahriman.web.controller.kpimonitor.PlayerRemainController;

@Service
@CacheConfig(cacheNames="playerRemain")
public class PlayerRemainService implements DailyTask {

	private static final Logger logger = Logger.getLogger(PlayerRemainController.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private PlayerRemainMapper playerRemainMapper;
	
	/**
	 * 获取KPI活跃用户
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,-7,null);
		
		TableData result = new TableData();
		result.setCode(0);
		
		List<PlayerRemain> list;
		Long count;
		
		if(RequestPayload.containToday(copy)) {
			//查询期限包含今天
			doRealtimeStatistic();
			
			Date critical = playerRemainMapper.getEarlyDate();
			
			RequestPayload temp = new RequestPayload(copy);
			temp.setEnd(critical);//主表的查询截止日期设置为服务器最后一次统计日期的7天前
			
			list = playerRemainMapper.getMixinStatistic(temp,pageAndSort);
			count = playerRemainMapper.getMixinStatisticCount(temp, pageAndSort);
		} else {
			//查询期限不包含今天
			list = playerRemainMapper.getStatistic(copy,pageAndSort);
			count = playerRemainMapper.getStatisticCount(copy, pageAndSort);
		}
		
		result.setData(list);
		result.setCount(count);
		result.setExtra(RequestPayload.unPrepare(copy));
		
		return result;
	}
	
	/**
	 * 查询实时数据，并放进临时表
	 */
	private void doRealtimeStatistic() {
		List<AppServer> serverList = appServerService.getAllServer();
		
		Date startDate = playerRemainMapper.getEarlyDate();//临时表统计的开始日期设置为永久表没完成统计的最早日期
		
		Calendar now = Calendar.getInstance();
		now.setTime(DateUtils.getTodayTime0());
		now.add(Calendar.DAY_OF_MONTH, 1);
		Date endDate = now.getTime(); //临时表统计的截止日期为明天凌晨0点
		
		playerRemainMapper.createTempTable();
		for(AppServer server : serverList) {
			doTask(startDate,endDate,server,"t_today_player_remain");
		}
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return playerRemainMapper.getAllPlatform();
	}
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	@Override
	@CacheEvict(allEntries=true)
	public void run() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			task(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的玩家留存信息
	 * @param server 游戏日志服务器信息
	 */
	public void task(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		
		Date lastCountDate = playerRemainMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doTask(startDate, endDate, server, "t_player_remain");
	}
	
	private void doTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			CallableStatement stmt = conn.prepareCall("call proc_get_player_retention(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			ResultSet resultSet = stmt.executeQuery();
			final int batchSize = 100; // 每次批量插入的阈值
			List<PlayerRemain> recordList = new ArrayList<>(batchSize*2);
			while(resultSet.next()) {
				PlayerRemain record = resultMap(resultSet);
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					playerRemainMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				playerRemainMapper.batchInsert(recordList,storedTable);
			}
			
			//然后更新需要更新的字段
			endDate = startDate; // 结束日期设置为开始日期
			startDate = playerRemainMapper.getEarlyDate(); //开始日期设置为未完成统计的最早日期
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				PlayerRemain record = resultMap(resultSet);
				
				if(record.getDay2() != null || record.getDay3() != null || 
				   record.getDay4() != null || record.getDay5() != null || record.getDay6() != null || 
				   record.getDay7() != null || record.getDay14() != null || record.getDay30() != null) {
						playerRemainMapper.updateByUniqueKeySelective(record);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色留存数据查询失败", e.getCause());
			throw new RuntimeException("角色留存数据查询失败"); // 需要回滚事务
		}
	}
	
	private PlayerRemain resultMap(ResultSet resultSet) throws SQLException {
		Date date = resultSet.getDate(1);
		String platform = resultSet.getString(2);
		BigDecimal day1 = resultSet.getBigDecimal(3);
		BigDecimal day2 = resultSet.getBigDecimal(4);
		BigDecimal day3 = resultSet.getBigDecimal(5);
		BigDecimal day4 = resultSet.getBigDecimal(6);
		BigDecimal day5 = resultSet.getBigDecimal(7);
		BigDecimal day6 = resultSet.getBigDecimal(8);
		BigDecimal day7 = resultSet.getBigDecimal(9);
		BigDecimal day14 = resultSet.getBigDecimal(10);
		BigDecimal day30 = resultSet.getBigDecimal(11);

		PlayerRemain result = new PlayerRemain();
		result.setDate(date);
		result.setPlatform(platform);
		result.setDay1(day1);
		result.setDay2(day2);
		result.setDay3(day3);
		result.setDay4(day4);
		result.setDay5(day5);
		result.setDay6(day6);
		result.setDay7(day7);
		result.setDay14(day14);
		result.setDay30(day30);
		
		return result;
	}

}
