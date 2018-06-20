package com.syhd.ahriman.service.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syhd.ahriman.dao.mapper.PlayerRemainMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.PlayerRemain;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.DateUtils;
import com.syhd.ahriman.web.controller.kpimonitor.PlayerRemainController;

@Service
@CacheConfig(cacheNames="playerRemain")
public class PlayerRemainService {

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
	@Cacheable(sync=true)
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,-7);
		
		List<PlayerRemain> list = playerRemainMapper.getStatistic(copy,pageAndSort);
		Long count = playerRemainMapper.getStatisticCount(copy, pageAndSort);
		
		TableData result = new TableData();
		result.setCode(0);
		result.setData(list);
		result.setCount(count);
		result.setExtra(RequestPayload.unPrepare(copy));
		
		return result;
	}
	
	@CachePut
	@Transactional
	public int insert(PlayerRemain record) {
		return playerRemainMapper.insert(record);
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return playerRemainMapper.getAllPlatform();
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
	 * 每天执行
	 */
	@Scheduled(cron="0 0 0 * * ?")
	public void dailyTask() {
		task();
	}
	
	@Transactional
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			playerRemainTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的玩家留存信息
	 * @param server 游戏日志服务器信息
	 */
	public void playerRemainTask(AppServer server) {
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
		
		doPlayerRemainTask(startDate, endDate, server, "t_player_remain");
	}
	
	private void doPlayerRemainTask(Date startDate, Date endDate, AppServer server, String storedTable) {
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

				PlayerRemain record = new PlayerRemain();
				record.setDate(date);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setDay1(day1);
				record.setDay2(day2);
				record.setDay3(day3);
				record.setDay4(day4);
				record.setDay5(day5);
				record.setDay6(day6);
				record.setDay7(day7);
				record.setDay14(day14);
				record.setDay30(day30);
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					playerRemainMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				playerRemainMapper.batchInsert(recordList,storedTable);
			}
			
			stmt.close();
			stmt = conn.prepareCall("call proc_get_player_retention_for_update(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			//然后更新需要更新的字段
			Date earlyNotCountDate = playerRemainMapper.getEarlyDate();
			stmt.setDate(1, DateUtils.conver2SqlDate(earlyNotCountDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
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
				
				PlayerRemain record = new PlayerRemain();
				record.setDate(date);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setDay1(day1);
				record.setDay2(day2);
				record.setDay3(day3);
				record.setDay4(day4);
				record.setDay5(day5);
				record.setDay6(day6);
				record.setDay7(day7);
				record.setDay14(day14);
				record.setDay30(day30);
				
				if(day2 != null || day3 != null || 
					day4 != null || day5 != null || day6 != null || 
					day7 != null || day14 != null || day30 != null) {
							playerRemainMapper.updateByUniqueKeySelective(record);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色留存数据查询失败", e.getCause());
			throw new RuntimeException("角色留存数据查询失败"); // 需要回滚事务
		}
	}
	
}
