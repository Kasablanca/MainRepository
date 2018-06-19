package com.syhd.ahriman.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.syhd.ahriman.dao.mapper.AppServerMapper;
import com.syhd.ahriman.dao.mapper.DailyRevenueMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.DailyRevenue;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.StatisticPayload;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.ArrayUtils;
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
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private CacheManager cacheManager;

	/**
	 * 获取KPI考核收入
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	@Cacheable(sync=true)
	public Result getDailyRevenue(RequestPayload param) {
		RequestPayload copy = new RequestPayload(param);
		
		Result result = Result.getErrorResult();
		
		String[] platforms = copy.getPlatform();
		if(platforms != null && ArrayUtils.contains(platforms, "-1")) {
			// 说明选择的全部平台，应该去掉此参数
			copy.setPlatform(null);
		}
		
		Integer[] serverIds = copy.getServerId();
		if(serverIds != null && ArrayUtils.contains(serverIds, -1)) {
			// 说明选择的全部服务器，应该去掉此参数
			copy.setServerId(null);
		}
		
		if(StringUtils.isEmpty(copy.getStart())) {
			// 若没给开始日期，则设置为上月同一天凌晨00:00:00
			copy.setStart(DateUtils.getOneMonthBeforeTime0());
		}
		if(StringUtils.isEmpty(copy.getEnd()))	{
			// 若没给结束日期，则设置为今天凌晨00:00:00.000
			copy.setEnd(DateUtils.getTodayTime0());
		} else {
			// 若已给截止日期，则判断是否超过今天凌晨00:00:00
			if(!copy.getEnd().before(DateUtils.getTodayTime0())) {
				// 说明 param.end >= 今天凌晨,则需要查询原始数据库和当前数据库的混合数据
				return getMixinRevenue(copy);
			} else {
				// 说明在今天凌晨之前，则将截止日期设置为第二天凌晨
				Calendar now = Calendar.getInstance();
				now.setTime(copy.getEnd());
				now.add(Calendar.DAY_OF_MONTH, 1);
				copy.setEnd(now.getTime());
			}
		}
		
		result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			return result;
		}
		
		ExchangeRate rate = (ExchangeRate) result.getData();
		result = Result.getSuccessResult();
		List<KpiStatistic> list = dailyRevenueMapper.getStatistic(copy,rate);
		StatisticPayload data = new StatisticPayload(KpiStatistic.fill(list, copy.getStart(), copy.getEnd()), copy);
		result.setData(data);
		
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
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	/**
	 * 查询原始数据库和当前数据库的混合数据
	 * @param param 查询参数
	 * @return 查询结果或失败信息
	 */
	@Transactional
	public Result getMixinRevenue(RequestPayload param) {
		Result result = Result.getErrorResult();
		
		List<AppServer> serverList = appServerMapper.getAllServer();
		
		for(AppServer server : serverList) {
			String url = appServerService.getLogDbUrl(server.getLogDb());
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
		List<KpiStatistic> list = dailyRevenueMapper.getMixinStatistic(param,rate);
		StatisticPayload data = new StatisticPayload(KpiStatistic.fill(list, param.getStart(), param.getEnd()), param);
		result.setData(data);
		return result;
	}
	
	/**
	 * 抓取每个日志服务器的KPI信息
	 * @param server 游戏日志服务器信息
	 */
	@Async
	@Transactional
	public void initFetch(AppServer server,Date yesterday,java.sql.Date endDate) {
		Date lastCountDate = dailyRevenueMapper.getEndDateByServerId(server.getServerid());
		if(lastCountDate == null || lastCountDate.compareTo(yesterday) <= 0) {
			// 如果昨天或者之前的日期没有统计，则需要在系统启动时进行统计
			String url = appServerService.getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {	
				PreparedStatement stmt = null;
				
				if(lastCountDate == null) {
					// 说明没任何统计，则统计截止今天凌晨00:00:00的数据
					stmt = conn.prepareStatement(
							"select date,platform,moneyType,sum(money) money from " + 
							"(select date(date) date,platform,rmb money,lv moneyType from t_rechargelog where date < ?) t "+
							"group by date,platform,moneyType");
					
					stmt.setDate(1, endDate);
				} else {
					// 说明有统计，则判断统计信息是否已经最新
					stmt = conn.prepareStatement("select count(*) from t_rechargelog where date >= ?");
					
					Calendar now = Calendar.getInstance();
					now.setTime(lastCountDate);
					now.add(Calendar.DAY_OF_MONTH, 1);
					java.sql.Date critical = DateUtils.conver2SqlDate(now.getTime());
					stmt.setDate(1, critical);
					
					ResultSet resultSet = stmt.executeQuery();
					if(resultSet.next()) {
						int count = resultSet.getInt(1);
						if(count == 0) return; // 说明主数据库的信息已经是最新的
					}
					stmt.close();
					
					// 说明有统计，则统计从第二天截止今天凌晨00:00:00的数据
					stmt = conn.prepareStatement(
							"select date,platform,moneyType,sum(money) money from " + 
							"(select date(date) date,platform,rmb money,lv moneyType from t_rechargelog where date >= ? and date < ?) t "+
							"group by date,platform,moneyType");
					
					stmt.setDate(1, critical); // 统计开始日期(包含)
					stmt.setDate(2, endDate); // 统计截止日期(不包含)
				}
				
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
						dailyRevenueMapper.batchInsert(revenueList);
						revenueList.clear();
					}
				}
				if(revenueList.size() > 0) {
					dailyRevenueMapper.batchInsert(revenueList);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("KPI考核收入查询失败", e.getCause());
				throw new RuntimeException("KPI考核收入查询失败"); // 需要回滚事务
			}
		}
	}
	
	/**
	 * 初始化关于KPI考核收入的预查询的定时任务,每天凌晨0点执行
	 */
	@Transactional
	@Scheduled(cron="0 0 0 * * ?")
	public void kpiAppraiseRevenueTask() {
		cacheManager.getCache("KpiAppraiseRevenue").clear(); // 手动清除缓存
		
		List<AppServer> serverList = appServerMapper.getAllServer();

		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date startDate = DateUtils.conver2SqlDate(yesterday);// 昨天凌晨00:00:00
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		
		for(AppServer server : serverList) {
			Date lastCountDate = dailyRevenueMapper.getEndDateByServerId(server.getServerid());
			if(lastCountDate == null || lastCountDate.compareTo(yesterday) < 0) {
				// 说明昨天的或之前的数据还没统计
				String url = appServerService.getLogDbUrl(server.getLogDb());
				String user = gamelogProperties.getUsername();
				String password = gamelogProperties.getPassword();
				try(Connection conn = DriverManager.getConnection(url, user, password)) {
					PreparedStatement stmt = conn.prepareStatement(
							"select platform,moneyType,sum(money) money from " + 
							"(select platform,rmb money,lv moneyType from t_rechargelog where date >= ? and date < ?) t "+
							"group by platform,moneyType");
					
					stmt.setDate(1, startDate); // 昨天凌晨00:00:00
					stmt.setDate(2, endDate); // 当天凌晨00:00:00
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
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
					break;
				}
			}
		}
		System.gc();
	}
	
}