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

import com.syhd.ahriman.dao.mapper.DailyNewRoleMapper;
import com.syhd.ahriman.dao.mapper.UserInfoMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.DailyNewRole;
import com.syhd.ahriman.dto.KpiStatistic;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.StatisticPayload;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.ArrayUtils;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="newuser")
public class NewUserService {
	
	private static Logger logger = Logger.getLogger(NewUserService.class);
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private DailyNewRoleMapper dailyNewRoleMapper;
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private CacheManager cacheManager;

	/**
	 * 获取KPI新增用户数据
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	@Cacheable(sync=true)
	public Result getStatistic(RequestPayload param) {
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
				return getMixinStatistic(copy);
			} else {
				// 说明在今天凌晨之前，则将截止日期设置为第二天凌晨
				Calendar now = Calendar.getInstance();
				now.setTime(copy.getEnd());
				now.add(Calendar.DAY_OF_MONTH, 1);
				copy.setEnd(now.getTime());
			}
		}
		
		result = Result.getSuccessResult();
		
		Map<String,Object> response = new HashMap<>();
		response.put("newRole", KpiStatistic.fill(dailyNewRoleMapper.getStatistic(copy), copy.getStart(), copy.getEnd()));
		response.put("newAccount", KpiStatistic.fill(userInfoMapper.getStatistic(copy), copy.getStart(), copy.getEnd()));
		
		StatisticPayload data = new StatisticPayload(response, copy);
		result.setData(data);
		return result;
	}
	
	@CachePut
	@Transactional
	public int insert(DailyNewRole record) {
		return dailyNewRoleMapper.insert(record);
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return dailyNewRoleMapper.getAllPlatform();
	}
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	/**
	 * 查询原始数据库和当前数据库的混合数据
	 * @param param 查询参数
	 * @return 查询结果或失败信息
	 */
	@Transactional
	public Result getMixinStatistic(RequestPayload param) {
		Result result = Result.getErrorResult();
		
		java.sql.Date start = DateUtils.conver2SqlDate(new Date()); // 统计开始日期(包含)
		
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			String url = appServerService.getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {
				// 从日志数据库查询从今天凌晨开始的数据，并存入系统数据库的临时表
				PreparedStatement stmt = conn.prepareStatement(
						"select date,platform,count(pid) count from " + 
						"(select date(date) date,platform,pid from t_createplayerlog where date >= ?) t "+
						"group by date,platform");

				stmt.setDate(1, start);
				ResultSet resultSet = stmt.executeQuery();
				
				dailyNewRoleMapper.createTodayNewRoleTable(); // 创建临时后才能插入数据
				while(resultSet.next()) {
					java.sql.Date date = resultSet.getDate(1);
					String platform = resultSet.getString(2);
					Integer count = resultSet.getInt(3);

					DailyNewRole record = new DailyNewRole();
					record.setDate(date);
					record.setPlatform(platform);
					record.setServerId(server.getServerid());
					record.setServerName(server.getServername());
					record.setCount(count);
					dailyNewRoleMapper.insertIntoTemp(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("实时查询KPI考核收入数据失败", e.getCause());
				result.setMessage("实时查询KPI考核收入数据失败");
				return result;
			}
		}
		
		result = Result.getSuccessResult();
		
		Map<String,Object> data = new HashMap<>();
		data.put("newRole", KpiStatistic.fill(dailyNewRoleMapper.getMixinStatistic(param), param.getStart(), param.getEnd()));
		data.put("newAccount", KpiStatistic.fill(userInfoMapper.getStatistic(param), param.getStart(), param.getEnd()));
		
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
		Date lastCountDate = dailyNewRoleMapper.getLastCountDate(server.getServerid());
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
							"select date,platform,count(pid) count from " + 
							"(select date(date) date,platform,pid from t_createplayerlog where date < ?) t "+
							"group by date,platform");
					
					java.sql.Date end = DateUtils.conver2SqlDate(endDate); // 统计截止日期(不包含)
					stmt.setDate(1, end);
				} else {
					// 说明有统计，则判断统计信息是否已经最新
					stmt = conn.prepareStatement("select count(*) from t_createplayerlog where date >= ?");
					
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
							"select date,platform,count(pid) from " + 
							"(select date(date) date,platform,pid from t_createplayerlog where date >= ? and date < ?) t "+
							"group by date,platform");
					
					stmt.setDate(1, critical); // 统计开始日期(包含)
					stmt.setDate(2, endDate); // 统计截止日期(不包含)
				}
				
				ResultSet resultSet = stmt.executeQuery();
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
						dailyNewRoleMapper.batchInsert(recordList);
						recordList.clear();
					}
				}
				if(recordList.size() > 0) {
					dailyNewRoleMapper.batchInsert(recordList);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("KPI新增用户查询失败", e.getCause());
				throw new RuntimeException("KPI新增查询失败"); // 需要回滚事务
			}
		}
	}
	
	/**
	 * 初始化关于KPI考核收入的预查询的定时任务,每天凌晨0点执行
	 */
	@Transactional
	@Scheduled(cron="0 0 0 * * ?")
	public void kpiAppraiseRevenueTask() {
		cacheManager.getCache("newuser").clear(); // 手动清除缓存
		
		List<AppServer> serverList = appServerService.getAllServer();

		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date startDate = DateUtils.conver2SqlDate(yesterday);// 昨天凌晨00:00:00
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		
		for(AppServer server : serverList) {
			Date lastCountDate = dailyNewRoleMapper.getLastCountDate(server.getServerid());
			if(lastCountDate == null || lastCountDate.compareTo(yesterday) < 0) {
				// 说明昨天的或之前的数据还没统计
				String url = appServerService.getLogDbUrl(server.getLogDb());
				String user = gamelogProperties.getUsername();
				String password = gamelogProperties.getPassword();
				try(Connection conn = DriverManager.getConnection(url, user, password)) {
					PreparedStatement stmt = conn.prepareStatement(
							"select platform,count(pid) from " + 
							"(select platform,pid from t_createplayerlog where date >= ? and date < ?) t "+
							"group by platform");
					
					stmt.setDate(1, startDate);
					stmt.setDate(2, endDate);
					ResultSet resultSet = stmt.executeQuery();
					
					while(resultSet.next()) {
						String platform = resultSet.getString(1);
						Integer count = resultSet.getInt(2);
						
						DailyNewRole record = new DailyNewRole();
						record.setDate(new java.util.Date());
						record.setPlatform(platform);
						record.setServerId(server.getServerid());
						record.setServerName(server.getServername());
						record.setCount(count);
						
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
