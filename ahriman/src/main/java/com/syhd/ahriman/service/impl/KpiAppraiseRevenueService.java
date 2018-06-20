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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syhd.ahriman.dao.mapper.DailyRevenueMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.DailyRevenue;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.StatisticPayload;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="KpiAppraiseRevenue")
public class KpiAppraiseRevenueService {
	
	private static Logger logger = Logger.getLogger(KpiAppraiseRevenueService.class);
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private DailyRevenueMapper dailyRevenueMapper;
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@Autowired
	private AppServerService appServerService;

	/**
	 * 获取KPI考核收入
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	@Cacheable(sync=true)
	public Result getDailyRevenue(RequestPayload param) {
		RequestPayload copy = RequestPayload.prepare(param,null);
		
		Result result = Result.getErrorResult();
		
		result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			return result;
		}
		ExchangeRate rate = (ExchangeRate) result.getData();
		
		List<KpiStatistic> list = null;
		if(RequestPayload.containToday(copy)) {
			//查询期限包含今天
			doRealtimeStatistic();
			list = dailyRevenueMapper.getMixinStatistic(copy,rate);
		} else {
			//查询期限不包含今天
			list = dailyRevenueMapper.getStatistic(copy,rate);
		}
		list = KpiStatistic.fill(list, copy.getStart(), copy.getEnd());
		
		result = Result.getSuccessResult();
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
		startDate = now.getTime();
		Date endDate = now.getTime();
		
		dailyRevenueMapper.createTodayRevenueTable();
		for(AppServer server : serverList) {
			doRevenueTask(startDate,endDate,server,"t_today_revenue");
		}
	}
	
	@CachePut
	@Transactional
	public int insert(DailyRevenue record) {
		return dailyRevenueMapper.insert(record);
	}
	
	@Cacheable(key="#root.method")
	public List<DailyRevenue> getAllServer() {
		return dailyRevenueMapper.getAllServer();
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return dailyRevenueMapper.getAllPlatform();
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
			revenueTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的收入信息
	 * @param server 游戏日志服务器信息
	 */
	public void revenueTask(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		Date lastCountDate = dailyRevenueMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doRevenueTask(startDate, endDate, server, "t_daily_revenue");
	}
	
	private void doRevenueTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			PreparedStatement stmt = conn.prepareStatement(
					"select date,platform,moneyType,sum(money) money from " + 
					"(select date(date) date,platform,rmb money,lv moneyType from t_rechargelog where date >= ? and date < ?) t "+
					"group by date,platform,moneyType");
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			ResultSet resultSet = stmt.executeQuery();
			final int batchSize = 100; // 每次批量插入的阈值
			List<DailyRevenue> revenueList = new ArrayList<>(batchSize*2);
			while(resultSet.next()) {
				Date date = resultSet.getDate(1);
				String platform = resultSet.getString(2);
				Integer moneyType = resultSet.getInt(3);
				Integer money = resultSet.getInt(4);

				DailyRevenue record = new DailyRevenue();
				record.setDate(date);
				record.setMoney(money);
				record.setMoneyType(moneyType);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setServerName(server.getServername());
				revenueList.add(record);
				
				if(revenueList.size() == batchSize) {
					dailyRevenueMapper.batchInsert(revenueList,storedTable);
					revenueList.clear();
				}
			}
			if(revenueList.size() > 0) {
				dailyRevenueMapper.batchInsert(revenueList,storedTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("日收入数据查询失败", e.getCause());
			throw new RuntimeException("日收入数据查询失败"); // 需要回滚事务
		}
	}
	
}
