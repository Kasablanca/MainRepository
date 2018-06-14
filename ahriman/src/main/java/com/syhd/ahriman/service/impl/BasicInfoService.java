package com.syhd.ahriman.service.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
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

import com.syhd.ahriman.dao.mapper.BasicInfoMapper;
import com.syhd.ahriman.dao.mapper.UserRegisteredMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.BasicInfo;
import com.syhd.ahriman.dao.model.UserRegistered;
import com.syhd.ahriman.dto.BasicInfoVO;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.ArrayUtils;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="basicInfo")
public class BasicInfoService {

	private static final Logger logger = Logger.getLogger(BasicInfoService.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private BasicInfoMapper basicInfoMapper;
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@Autowired
	private UserRegisteredMapper userRegisteredMapper;
	
	/**
	 * 获取KPI活跃用户
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	@Cacheable(sync=true)
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = new RequestPayload(param);
		
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
			// 若没给开始日期，则设置为上周同一天凌晨00:00:00
			copy.setStart(DateUtils.getOneWeekBeforeTime0());
		}
		if(StringUtils.isEmpty(copy.getEnd()))	{
			// 若没给结束日期，则设置为今天凌晨00:00:00.000
			copy.setEnd(DateUtils.getTodayTime0());
		} else {
			// 若已给截止日期，则判断是否超过今天凌晨00:00:00
			if(!copy.getEnd().before(DateUtils.getTodayTime0())) {
				// 说明 param.end >= 今天凌晨,则需要查询原始数据库和当前数据库的混合数据
				// return getMixinStatistic(copy,pageAndSort); 最后一天的不查询
			} else {
				// 说明在今天凌晨之前，则将截止日期设置为第二天凌晨
				Calendar now = Calendar.getInstance();
				now.setTime(copy.getEnd());
				now.add(Calendar.DAY_OF_MONTH, 1);
				copy.setEnd(now.getTime());
			}
		}
		
		TableData result = new TableData();
		List<BasicInfoVO> list = basicInfoMapper.getStatistic(copy,pageAndSort);
		result.setData(list);
		result.setCode(0);
		result.setCount(basicInfoMapper.getStatisticCount(copy, pageAndSort));
		
		return result;
	}
	
	@CachePut
	@Transactional
	public int insert(BasicInfo record) {
		return basicInfoMapper.insert(record);
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return basicInfoMapper.getAllPlatform();
	}
	
/*====================================分割线,以下方法非对外使用====================================*/
	
	/**
	 * 查询原始数据库和当前数据库的混合数据
	 * @param param 查询参数
	 * @param pageAndSort 分页和排序参数
	 * @return 查询结果或失败信息
	 */
/*	@Transactional
	public TableData getMixinStatistic(RequestPayload param,PageAndSort pageAndSort) {
		TableData result = new TableData();
		
		Result temp = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(temp)) {
			result.setCode(-1);
			result.setMsg(temp.getMessage());
			logger.error("混合查询基础失败，原因："+temp.getMessage());
		}
		ExchangeRate rate = (ExchangeRate) temp.getData();// 汇率
		
		List<AppServer> serverList = appServerService.getAllServer();
		
		for(AppServer server : serverList) {
			String url = appServerService.getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {
				// 从日志数据库查询从今天凌晨开始的数据，并存入系统数据库的临时表
				CallableStatement stmt = conn.prepareCall("call proc_get_general_info(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setDate(1, DateUtils.conver2SqlDate(DateUtils.getTodayTime0()));
				stmt.setDate(2, null);
				stmt.setBigDecimal(3, rate.getUsd());
				stmt.setBigDecimal(4, rate.getTwd());
				stmt.setBigDecimal(5, rate.getHkd());
				
				ResultSet resultSet = stmt.executeQuery();
				
				final int batchSize = 100; // 每次批量插入的阈值
				List<BasicInfo> recordList = new ArrayList<>(batchSize*2);
				
				basicInfoMapper.createTodayKpiPayTable();
				while(resultSet.next()) {
					Date date = resultSet.getDate(1);
					String platform = resultSet.getString(2);
					BigDecimal totalRevenue = resultSet.getBigDecimal(3);
					BigDecimal dailyRevenue = resultSet.getBigDecimal(4);
					BigDecimal dailyRevenueNew = resultSet.getBigDecimal(5);
					Integer liveUser = resultSet.getInt(6);
					Integer liveUserNew = resultSet.getInt(7);
					Integer retentionDay2 = resultSet.getInt(8);
					Integer retentionDay3 = resultSet.getInt(9);
					Integer retentionDay5 = resultSet.getInt(10);
					Integer retentionDay7 = resultSet.getInt(11);
					Integer retentionDay15 = resultSet.getInt(12);
					Integer retentionDay30 = resultSet.getInt(13);
					Integer payUser = resultSet.getInt(14);
					Integer payUserNew = resultSet.getInt(15);

					BasicInfo record = new BasicInfo();
					record.setDate(date);
					record.setPlatform(platform);
					record.setServerId(server.getServerid());
					record.setDailyRevenue(dailyRevenue);
					record.setDailyRevenueNew(dailyRevenueNew);
					record.setLiveUser(liveUser);
					record.setLiveUserNew(liveUserNew);
					record.setPayUser(payUser);
					record.setPayUserNew(payUserNew);
					record.setRetentionDay15(retentionDay15);
					record.setRetentionDay2(retentionDay2);
					record.setRetentionDay3(retentionDay3);
					record.setRetentionDay30(retentionDay30);
					record.setRetentionDay5(retentionDay5);
					record.setRetentionDay7(retentionDay7);
					record.setTotalRevenue(totalRevenue);
					
					recordList.add(record);
					
					if(recordList.size() == batchSize) {
						basicInfoMapper.batchInsertTemp(recordList);
						recordList.clear();
					}
				}
				if(recordList.size() > 0) {
					basicInfoMapper.batchInsertTemp(recordList);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("实时综合数据-基础数据失败", e.getCause());
				result.setCode(-1);
				result.setMsg("实时综合数据-基础数据失败");
				return result;
			}
		}
		
		
		List<BasicInfoVO> list = basicInfoMapper.getMixinStatistic(param,pageAndSort);
		result.setData(list);
		result.setCode(0);
		result.setCount(basicInfoMapper.getMixinStatisticCount(param, pageAndSort));
		
		return result;
	}*/
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	/**
	 * 抓取用户注册表的每日累计注册数
	 * @param endDate 当天0点
	 */
	@Async
	@Transactional
	public void initUserRegisteredFetch(Date endDate) {
		userRegisteredAnalyzer(endDate);
	}
	
	/**
	 *  抓取用户注册表的每日累计注册数
	 * @param endDate endDate 当天0点
	 */
	private void userRegisteredAnalyzer(Date endDate) {
		Date lastCountDate = userRegisteredMapper.getLastCountDate();
		
		Date startDate = null;
		if(lastCountDate != null) {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			// 将开始日期设置为上次统计日期的第二天
			startDate = now.getTime();
		}
		List<UserRegistered> list = userRegisteredMapper.getUserRegistered(startDate,endDate);
		
		int batchSize = 100;
		for(int i = 0, size = list.size(); i < size; i+=batchSize) {
			List<UserRegistered> subList = list.subList(i, Math.min((i+batchSize), size));
			if(subList.size()>0) {
				userRegisteredMapper.batchInsert(subList);
			}
		}
	}
	
	/**
	 * 抓取用户注册表的每日累计注册数的定时任务
	 */
	@Transactional
	@Scheduled(cron="0 0 0 * * ?")
	public void userRegisteredTask() {
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		userRegisteredAnalyzer(endDate);
	}
	
	/**
	 * 抓取每个日志服务器的综合数据-基础数据信息
	 * @param server 游戏日志服务器信息
	 */
	@Async
	@Transactional
	public void initFetch(AppServer server,Date yesterday,java.sql.Date endDate) {
		Result result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			logger.error("综合数据-基础数据初始查询失败，原因："+result.getMessage());
			return;
		}
		ExchangeRate rate = (ExchangeRate) result.getData();// 汇率
		
		Date lastCountDate = basicInfoMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null || lastCountDate.compareTo(yesterday) <= 0) {
			// 如果昨天或者之前的日期没有统计，则需要在系统启动时进行统计
			String url = appServerService.getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {	
				CallableStatement stmt = conn.prepareCall("call proc_get_general_info(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setBigDecimal(3, rate.getUsd());
				stmt.setBigDecimal(4, rate.getTwd());
				stmt.setBigDecimal(5, rate.getHkd());
				
				if(lastCountDate == null) {
					// 说明没任何统计，则统计截止今天凌晨00:00:00的数据
					stmt.setDate(1, null);
					stmt.setDate(2, endDate);
				} else {
					// 说明有统计，则判断统计信息是否已经最新
					PreparedStatement query = conn.prepareStatement("SELECT (SELECT count(*) FROM t_rechargelog WHERE date >= ?)+"+
							"(SELECT count(*) FROM t_loginlog WHERE date >= ?)+"+
							"(SELECT count(*) FROM t_createplayerlog WHERE date >= ?) count");
					
					Calendar now = Calendar.getInstance();
					now.setTime(lastCountDate);
					now.add(Calendar.DAY_OF_MONTH, 1);
					java.sql.Date critical = DateUtils.conver2SqlDate(now.getTime());
					query.setDate(1, critical);
					query.setDate(2, critical);
					query.setDate(3, critical);
					
					ResultSet resultSet = query.executeQuery();
					if(resultSet.next()) {
						int count = resultSet.getInt(1);
						if(count == 0) return; // 说明主数据库的信息已经是最新的
					}
					query.close();
					
					// 说明主数据库信息不是最新，则统计从第二天截止今天凌晨00:00:00的数据
					stmt.setDate(1, critical); // 统计开始日期(包含)
					stmt.setDate(2, endDate); // 统计截止日期(不包含)
				}
				
				ResultSet resultSet = stmt.executeQuery();
				final int batchSize = 100; // 每次批量插入的阈值
				List<BasicInfo> recordList = new ArrayList<>(batchSize*2);
				while(resultSet.next()) {
					Date date = resultSet.getDate(1);
					String platform = resultSet.getString(2);
					BigDecimal totalRevenue = resultSet.getBigDecimal(3);
					BigDecimal dailyRevenue = resultSet.getBigDecimal(4);
					BigDecimal dailyRevenueNew = resultSet.getBigDecimal(5);
					Integer liveUser = resultSet.getInt(6);
					Integer liveUserNew = resultSet.getInt(7);
					Integer retentionDay2 = resultSet.getInt(8);
					Integer retentionDay3 = resultSet.getInt(9);
					Integer retentionDay5 = resultSet.getInt(10);
					Integer retentionDay7 = resultSet.getInt(11);
					Integer retentionDay15 = resultSet.getInt(12);
					Integer retentionDay30 = resultSet.getInt(13);
					Integer payUser = resultSet.getInt(14);
					Integer payUserNew = resultSet.getInt(15);

					BasicInfo record = new BasicInfo();
					record.setDate(date);
					record.setPlatform(platform);
					record.setServerId(server.getServerid());
					record.setDailyRevenue(dailyRevenue);
					record.setDailyRevenueNew(dailyRevenueNew);
					record.setLiveUser(liveUser);
					record.setLiveUserNew(liveUserNew);
					record.setPayUser(payUser);
					record.setPayUserNew(payUserNew);
					record.setRetentionDay15(retentionDay15);
					record.setRetentionDay2(retentionDay2);
					record.setRetentionDay3(retentionDay3);
					record.setRetentionDay30(retentionDay30);
					record.setRetentionDay5(retentionDay5);
					record.setRetentionDay7(retentionDay7);
					record.setTotalRevenue(totalRevenue);
					
					recordList.add(record);
					
					if(recordList.size() == batchSize) {
						basicInfoMapper.batchInsert(recordList);
						recordList.clear();
					}
				}
				if(recordList.size() > 0) {
					basicInfoMapper.batchInsert(recordList);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("综合数据-基础数据初始查询失败", e.getCause());
				throw new RuntimeException("综合数据-基础数据初始查询失败"); // 需要回滚事务
			}
		}
	}
	
	/**
	 * 初始化关于综合数据-基础数据的预查询的定时任务,每天凌晨0点执行
	 */
	@Transactional
	@Scheduled(cron="0 0 0 * * ?")
	public void basicInfoTask() {
		cacheManager.getCache("basicInfo").clear(); // 手动清除缓存
		
		Result result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			logger.error("付综合数据-基础数据初始查询失败，原因："+result.getMessage());
			return;
		}
		ExchangeRate rate = (ExchangeRate) result.getData();// 汇率
		
		List<AppServer> serverList = appServerService.getAllServer();

		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date startDate = DateUtils.conver2SqlDate(yesterday);// 昨天凌晨00:00:00
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		
		for(AppServer server : serverList) {
			Date lastCountDate = basicInfoMapper.getLastCountDate(server.getServerid());
			if(lastCountDate == null || lastCountDate.compareTo(yesterday) < 0) {
				// 说明昨天的或之前的数据还没统计
				String url = appServerService.getLogDbUrl(server.getLogDb());
				String user = gamelogProperties.getUsername();
				String password = gamelogProperties.getPassword();
				try(Connection conn = DriverManager.getConnection(url, user, password)) {
					CallableStatement stmt = conn.prepareCall("call proc_get_general_info(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setBigDecimal(3, rate.getUsd());
					stmt.setBigDecimal(4, rate.getTwd());
					stmt.setBigDecimal(5, rate.getHkd());
					
					stmt.setDate(1, startDate);
					stmt.setDate(2, endDate); 
					ResultSet resultSet = stmt.executeQuery();
					final int batchSize = 100; // 每次批量插入的阈值
					List<BasicInfo> recordList = new ArrayList<>(batchSize*2);
					
					while(resultSet.next()) {
						Date date = resultSet.getDate(1);
						String platform = resultSet.getString(2);
						BigDecimal totalRevenue = resultSet.getBigDecimal(3);
						BigDecimal dailyRevenue = resultSet.getBigDecimal(4);
						BigDecimal dailyRevenueNew = resultSet.getBigDecimal(5);
						Integer liveUser = resultSet.getInt(6);
						Integer liveUserNew = resultSet.getInt(7);
						Integer retentionDay2 = resultSet.getInt(8);
						Integer retentionDay3 = resultSet.getInt(9);
						Integer retentionDay5 = resultSet.getInt(10);
						Integer retentionDay7 = resultSet.getInt(11);
						Integer retentionDay15 = resultSet.getInt(12);
						Integer retentionDay30 = resultSet.getInt(13);
						Integer payUser = resultSet.getInt(14);
						Integer payUserNew = resultSet.getInt(15);

						BasicInfo record = new BasicInfo();
						record.setDate(date);
						record.setPlatform(platform);
						record.setServerId(server.getServerid());
						record.setDailyRevenue(dailyRevenue == null ? BigDecimal.ZERO : dailyRevenue);
						record.setDailyRevenueNew(dailyRevenueNew == null ? BigDecimal.ZERO : dailyRevenueNew);
						record.setLiveUser(liveUser == null ? 0 : liveUser);
						record.setLiveUserNew(liveUserNew == null ? 0 : liveUserNew);
						record.setPayUser(payUser == null ? 0 : payUser);
						record.setPayUserNew(payUserNew == null ? 0 : payUserNew);
						record.setRetentionDay15(retentionDay15 == null ? 0 : retentionDay15);
						record.setRetentionDay2(retentionDay2 == null ? 0 : retentionDay2);
						record.setRetentionDay3(retentionDay3 == null ? 0 : retentionDay3);
						record.setRetentionDay30(retentionDay30 == null ? 0 : retentionDay30);
						record.setRetentionDay5(retentionDay5 == null ? 0 : retentionDay5);
						record.setRetentionDay7(retentionDay7 == null ? 0 : retentionDay7);
						record.setTotalRevenue(totalRevenue);
						
						recordList.add(record);
						
						if(recordList.size() == batchSize) {
							basicInfoMapper.batchInsert(recordList);
							recordList.clear();
						}
					}
					if(recordList.size() > 0) {
						basicInfoMapper.batchInsert(recordList);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("定时查询综合数据-基础数据失败", e.getCause());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
					break;
				}
			}
		}
		System.gc();
	}
	
}
