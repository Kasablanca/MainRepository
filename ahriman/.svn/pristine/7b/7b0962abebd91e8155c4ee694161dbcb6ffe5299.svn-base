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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.BasicInfoMapper;
import com.syhd.ahriman.dao.mapper.CommonMapper;
import com.syhd.ahriman.dao.mapper.UserInfoMapper;
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
import com.syhd.ahriman.service.CronTask;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="basicInfo")
public class BasicInfoService {

	private static final Logger logger = LoggerFactory.getLogger(BasicInfoService.class);
	
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
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	/**
	 * 获取KPI活跃用户
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	@Cacheable(sync=true)
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,null,null);
		
		TableData result = new TableData();
		result.setCode(0);
		
		List<BasicInfoVO> list;
		Long count;
		
		commonMapper.insertTDate(copy.getStart(), copy.getEnd());
		if(copy.getServerId() == null) {
			list = basicInfoMapper.getStatisticWithUserRegistered(copy,pageAndSort);
			count = basicInfoMapper.getStatisticCountWithUserRegistered(copy, pageAndSort);
		} else {
			list = basicInfoMapper.getStatistic(copy,pageAndSort);
			count = basicInfoMapper.getStatisticCount(copy, pageAndSort);
		}
		result.setData(list);
		result.setCount(count);
		result.setExtra(RequestPayload.unPrepare(copy));
		
		return result;
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return basicInfoMapper.getAllPlatform();
	}
	
/*====================================分割线,以下方法非对外使用====================================*/
	
	@CronTask("0 0 0 * * ?")
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			basicInfoTask(server);
		}
		userRegisterTask();
	}
	
	/**
	 * 抓取用户注册表的每日累计注册数
	 */
	public void userRegisterTask() {
		Date lastCountDate = userRegisteredMapper.getLastCountDate();
		
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		if(lastCountDate != null) {
			int count = userInfoMapper.getCountByDate(lastCountDate);
			if(count == 0)
				//说明不需要统计
				return;
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
			CallableStatement stmt = conn.prepareCall("call proc_get_general_info(?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			stmt.setString(3, rate.toString());
			
			ResultSet resultSet = stmt.executeQuery();
			resultSet.setFetchSize(100);
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
					basicInfoMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				basicInfoMapper.batchInsert(recordList,storedTable);
			}
		} catch (Exception e) {
			logger.error("综合数据-基础数据初始查询失败", e.getCause());
			throw new RuntimeException("综合数据-基础数据初始查询失败"); // 需要回滚事务
		}
	}
	
}
