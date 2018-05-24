package com.syhd.ahriman.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.springframework.util.StringUtils;

import com.syhd.ahriman.dao.mapper.AppServerMapper;
import com.syhd.ahriman.dao.mapper.DailyRevenueMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.DailyRevenue;
import com.syhd.ahriman.dto.DailyRevenueRequestParam;
import com.syhd.ahriman.dto.DailyRevenueVO;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="KpiAppraiseRevenue")
public class KpiAppraiseRevenueService {
	
	private static Logger logger = Logger.getLogger(KpiAppraiseRevenueService.class);
	
	@Autowired
	private AppServerMapper appServerMapper;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private DailyRevenueMapper dailyRevenueMapper;
	
	@Autowired
	private ExchangeRateService exchangeRateService;

	/**
	 * 获取KPI考核收入
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	@Cacheable(sync=true)
	public Result getDailyRevenue(DailyRevenueRequestParam param) {
		Result result = Result.getErrorResult();
		
		if(StringUtils.isEmpty(param.getStart())) {
			// 若没给开始日期，则设置为上月同一天凌晨00:00:00
			param.setStart(DateUtils.getOneMonthBeforeTime0());
		}
		if(StringUtils.isEmpty(param.getEnd()))	{
			// 若没给结束日期，则设置为今天凌晨00:00:00.000
			param.setEnd(DateUtils.getTodayTime0());
		} else {
			// 若已给截止日期，则判断是否超过今天凌晨00:00:00
			if(!param.getEnd().before(DateUtils.getTodayTime0())) {
				// 说明 param.end >= 今天凌晨,则需要查询原始数据库和当前数据库的混合数据
				return getMixinRevenue(param);
			} else {
				// 说明在今天凌晨之前，则将截止日期设置为第二天凌晨
				Calendar now = Calendar.getInstance();
				now.setTime(param.getEnd());
				now.add(Calendar.DAY_OF_MONTH, 1);
				param.setEnd(now.getTime());
			}
		}
		
		result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			return result;
		}
		
		ExchangeRate rate = (ExchangeRate) result.getData();
		result = Result.getSuccessResult();
		result.setData(dailyRevenueMapper.getDailyRevenue(param,rate));
		
		return result;
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
	
	/**
	 * 查询原始数据库和当前数据库的混合数据
	 * @param param 查询参数
	 * @return 查询结果或失败信息
	 */
	@Transactional
	private Result getMixinRevenue(DailyRevenueRequestParam param) {
		Result result = Result.getErrorResult();
		
		List<AppServer> serverList = appServerMapper.getAllServer();
		
		for(AppServer server : serverList) {
			String url = getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {
				// 从日志数据库查询从今天凌晨开始的数据，并存入系统数据库的临时表
				PreparedStatement stmt = conn.prepareStatement(
						"select date,platform,moneyType,sum(money) money from " + 
						"(select date(date) date,platform,rmb money,lv moneyType from t_rechargelog where date >= ?) t "+
						"group by date,platform,moneyType");

				java.sql.Date start = DateUtils.conver2SqlDate(new Date()); // 统计开始日期(包含)

				stmt.setDate(1, start);
				ResultSet resultSet = stmt.executeQuery();
				
				dailyRevenueMapper.createTodayRevenueTable(); // 创建临时后才能插入数据
				while(resultSet.next()) {
					java.sql.Date date = resultSet.getDate(1);
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
					dailyRevenueMapper.insertIntoTemp(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("实时查询KPI考核收入数据失败", e.getCause());
				result.setMessage("实时查询KPI考核收入数据失败");
				return result;
			}
		}
		
		result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			return result;
		}
		
		ExchangeRate rate = (ExchangeRate) result.getData();
		result = Result.getSuccessResult();
		List<DailyRevenueVO> data = dailyRevenueMapper.getMixinDailyRevenue(param,rate);
		result.setData(data);
		return result;
	}
	
	/*====================================分割线====================================*/
	
	@PostConstruct
	public void init() {
		preFetch();
	}
	
	@Async
	@Transactional
	private void preFetch() {
		List<AppServer> serverList = appServerMapper.getAllServer();
		
		Date yesterdayTime0 = DateUtils.getYesterdayTime0();
		for(AppServer server : serverList) {
			Date endDate = dailyRevenueMapper.getEndDateByServerId(server.getServerid());
			if(endDate == null || endDate.compareTo(yesterdayTime0) <= 0) {
				// 如果昨天或者之前的日期没有统计，则需要在系统启动时进行统计
				String url = getLogDbUrl(server.getLogDb());
				String user = gamelogProperties.getUsername();
				String password = gamelogProperties.getPassword();
				try(Connection conn = DriverManager.getConnection(url, user, password)) {	
					PreparedStatement stmt = null;
					
					if(endDate == null) {
						// 说明没任何统计，则统计截止今天凌晨00:00:00的数据
						stmt = conn.prepareStatement(
								"select date,platform,moneyType,sum(money) money from " + 
								"(select date(date) date,platform,rmb money,lv moneyType from t_rechargelog where date < ?) t "+
								"group by date,platform,moneyType");
						
						java.sql.Date end = DateUtils.conver2SqlDate(new Date()); // 统计截止日期(不包含)
						stmt.setDate(1, end);
					} else {
						// 说明有统计，则判断统计信息是否已经最新
						stmt = conn.prepareStatement("select count(*) from t_rechargelog where date >= ?");
						
						Calendar now = Calendar.getInstance();
						now.setTime(endDate);
						now.add(Calendar.DAY_OF_MONTH, 1);
						java.sql.Date critical = DateUtils.conver2SqlDate(now.getTime());
						stmt.setDate(1, critical);
						
						ResultSet resultSet = stmt.executeQuery();
						if(resultSet.next()) {
							int count = resultSet.getInt(1);
							if(count == 0) continue; // 说明主数据库的信息已经是最新的
						}
						stmt.close();
						
						// 说明有统计，则统计从第二天截止今天凌晨00:00:00的数据
						stmt = conn.prepareStatement(
								"select date,platform,moneyType,sum(money) money from " + 
								"(select date(date) date,platform,rmb money,lv moneyType from t_rechargelog where date >= ? and date < ?) t "+
								"group by date,platform,moneyType");

						java.sql.Date start = DateUtils.conver2SqlDate(critical);
						stmt.setDate(1, start); // 统计开始日期(包含)
						
						java.sql.Date end = DateUtils.conver2SqlDate(DateUtils.getTodayTime0());
						stmt.setDate(2, end); // 统计截止日期(不包含)
					}
					
					ResultSet resultSet = stmt.executeQuery();

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
						insert(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("KPI考核收入查询失败", e.getCause());
					throw new RuntimeException("KPI考核收入查询失败"); // 需要回滚事务
				}
			}
		}
	}
	
	/**
	 * 初始化关于KPI考核收入的预查询的定时任务,每天凌晨0点执行
	 */
	@Transactional
	@Scheduled(cron="0 0 0 * * ?")
	@CacheEvict
	public void kpiAppraiseRevenueTask() {
		List<AppServer> serverList = appServerMapper.getAllServer();

		Date yesterday = DateUtils.getYesterdayTime0();
		
		for(AppServer server : serverList) {
			Date endDate = dailyRevenueMapper.getEndDateByServerId(server.getServerid());
			if(endDate == null || endDate.compareTo(yesterday) < 0) {
				// 说明昨天的或之前的数据还没统计
				String url = getLogDbUrl(server.getLogDb());
				String user = gamelogProperties.getUsername();
				String password = gamelogProperties.getPassword();
				try(Connection conn = DriverManager.getConnection(url, user, password)) {
					PreparedStatement stmt = conn.prepareStatement(
							"select platform,moneyType,sum(money) money from " + 
							"(select platform,rmb money,lv moneyType from t_rechargelog where date >= ? and date < ?) t "+
							"group by platform,moneyType");
					
					stmt.setDate(1, DateUtils.conver2SqlDate(DateUtils.getYesterdayTime0())); // 昨天凌晨00:00:00
					stmt.setDate(2, DateUtils.conver2SqlDate(DateUtils.getTodayTime0())); // 当天凌晨00:00:00
					ResultSet resultSet = stmt.executeQuery();
					
					while(resultSet.next()) {
						String platform = resultSet.getString(1);
						Integer moneyType = resultSet.getInt(2);
						Integer money = resultSet.getInt(3);
						
						DailyRevenue record = new DailyRevenue();
						record.setDate(new java.util.Date());
						record.setMoney(money);
						record.setMoneyType(moneyType);
						record.setPlatform(platform);
						record.setServerId(server.getServerid());
						record.setServerName(server.getServername());
						
						insert(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("定时预查询KPI收入失败", e.getCause());
					continue;
				}
			}
		}
		System.gc();
	}
	
	/**
	 * 获取游戏日志服务器的URL
	 * @param logdb 原始URL值
	 * @return 有效的JDBC URL或抛出异常
	 */
	private String getLogDbUrl(String logdb) {
		try {
			int indexOfSlash = logdb.lastIndexOf("/");
			StringBuilder builder = new StringBuilder();
			builder
				.append(gamelogProperties.getPrefix())
				.append(logdb.substring(0, indexOfSlash))
				.append(":")
				.append(gamelogProperties.getPort())
				.append("/")
				.append(logdb.substring(indexOfSlash+1));
			return builder.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("无效的log_db值："+logdb);
		}
	}
	
}
