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
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	private BasicInfoMapper basicInfoMapper;
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@Autowired
	private UserRegisteredMapper userRegisteredMapper;
	
	@Autowired
	private CacheManager cacheManager;
	
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
			copy.setStart(DateUtils.getOneMonthBeforeTime0());
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
		result.setCode(0);
		List<BasicInfoVO> list;
		Long count;
		basicInfoMapper.insertTDate(copy.getStart(), copy.getEnd());
		if(copy.getServerId() == null) {
			list = basicInfoMapper.getStatisticWithUserRegistered(copy,pageAndSort);
			count = basicInfoMapper.getStatisticCountWithUserRegistered(copy, pageAndSort);
		} else {
			list = basicInfoMapper.getStatistic(copy,pageAndSort);
			count = basicInfoMapper.getStatisticCount(copy, pageAndSort);
		}
		result.setData(list);
		result.setCount(count);
		
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
	 * 每次服务器启动时执行
	 */
	@PostConstruct
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
	
	private void task() {
		cacheManager.getCache("basicInfo").clear();		// 手动清除缓存
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			basicInfoTask(server);
		}
		userRegisterTask();
	}
	
	/**
	 * 抓取用户注册表的每日累计注册数
	 */
	@Async
	@Transactional
	public void userRegisterTask() {
		Date lastCountDate = userRegisteredMapper.getLastCountDate();
		
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
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
			List<UserRegistered> subList = list.subList(i, Math.min(i+batchSize, size));
			if(subList.size()>0) {
				userRegisteredMapper.batchInsert(subList);
			}
		}
	}
	
	/**
	 * 抓取每个日志服务器的综合数据-基础数据信息
	 * @param server 游戏日志服务器信息
	 */
	@Async
	@Transactional
	public void basicInfoTask(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		
		Date lastCountDate = basicInfoMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doBasicInfoTask(startDate, endDate, server, "t_basic_info");
	}
	
	private void doBasicInfoTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		Result result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			logger.error("综合数据-基础数据初始查询失败，原因："+result.getMessage());
			return;
		}
		ExchangeRate rate = (ExchangeRate) result.getData();// 汇率
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			CallableStatement stmt = conn.prepareCall("call proc_get_general_info(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			stmt.setBigDecimal(3, rate.getUsd());
			stmt.setBigDecimal(4, rate.getTwd());
			stmt.setBigDecimal(5, rate.getHkd());
			
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
					basicInfoMapper.batchInsert(recordList,"t_basic_info");
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				basicInfoMapper.batchInsert(recordList,"t_basic_info");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("综合数据-基础数据初始查询失败", e.getCause());
			throw new RuntimeException("综合数据-基础数据初始查询失败"); // 需要回滚事务
		}
	}
	
}
