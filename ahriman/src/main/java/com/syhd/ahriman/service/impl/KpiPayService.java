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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syhd.ahriman.dao.mapper.KpiPayMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.KpiPay;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.KpiPayVO;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="kpiPay")
public class KpiPayService {

	private static final Logger logger = Logger.getLogger(KpiPayService.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private KpiPayMapper kpiPayMapper;
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	/**
	 * 获取KPI活跃用户
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,-7,null);
		
		List<KpiPayVO> list;
		Long count;
		if(RequestPayload.containToday(copy)) {
			//查询期限包含今天
			doRealtimeStatistic();
			list = kpiPayMapper.getMixinStatistic(copy,pageAndSort);
			count = kpiPayMapper.getMixinStatisticCount(copy, pageAndSort);
		} else {
			//查询期限不包含今天
			list = kpiPayMapper.getStatistic(copy,pageAndSort);
			count = kpiPayMapper.getStatisticCount(copy, pageAndSort);
		}
		
		TableData result = new TableData();
		result.setCode(0);
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
		
		Date startDate = DateUtils.getTodayTime0();
		Calendar now = Calendar.getInstance();
		now.setTime(startDate);
		now.add(Calendar.DAY_OF_MONTH, 1);
		Date endDate = now.getTime();
		
		kpiPayMapper.createTodayKpiPayTable();
		for(AppServer server : serverList) {
			doKpiPayTask(startDate,endDate,server,"t_today_kpi_pay");
		}
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return kpiPayMapper.getAllPlatform();
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
	//@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			kpiPayTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的玩家留存信息
	 * @param server 游戏日志服务器信息
	 */
	public void kpiPayTask(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		Date lastCountDate = kpiPayMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doKpiPayTask(startDate, endDate, server, "t_kpi_pay");
	}
	
	private void doKpiPayTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		Result temp = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(temp)) {
			logger.error("混合查询付费数据失败，原因："+temp.getMessage());
			throw new RuntimeException("混合查询付费数据失败，原因"+temp.getMessage());
		}
		ExchangeRate rate = (ExchangeRate) temp.getData();// 汇率
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			CallableStatement stmt = conn.prepareCall("call proc_get_pay_kpi(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			stmt.setBigDecimal(3, rate.getUsd());
			stmt.setBigDecimal(4, rate.getTwd());
			stmt.setBigDecimal(5, rate.getHkd());
			
			ResultSet resultSet = stmt.executeQuery();
			final int batchSize = 100; // 每次批量插入的阈值
			List<KpiPay> recordList = new ArrayList<>(batchSize*2);
			
			kpiPayMapper.createTodayKpiPayTable();
			while(resultSet.next()) {
				Date date = resultSet.getDate(1);
				String platform = resultSet.getString(2);
				Integer activeUserNumber = resultSet.getInt(3);
				BigDecimal revenue = resultSet.getBigDecimal(4);
				Integer payUserNumber = resultSet.getInt(5);
				Integer newPlayerNumber = resultSet.getInt(6);
				BigDecimal newPayRevenue = resultSet.getBigDecimal(7);
				Integer newPayNumber = resultSet.getInt(8);
				Integer oldPlayerNumber = resultSet.getInt(9);
				BigDecimal oldPayRevenue = resultSet.getBigDecimal(10);
				Integer oldPayNumber = resultSet.getInt(11);

				KpiPay record = new KpiPay();
				record.setDate(date);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setActiveUserNumber(activeUserNumber);
				record.setNewPayNumber(newPayNumber);
				record.setNewPayRevenue(newPayRevenue);
				record.setNewPlayerNumber(newPlayerNumber);
				record.setOldPayNumber(oldPayNumber);
				record.setOldPayRevenue(oldPayRevenue);
				record.setOldPlayerNumber(oldPlayerNumber);
				record.setPayUserNumber(payUserNumber);
				record.setRevenue(revenue);
				
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					kpiPayMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				kpiPayMapper.batchInsert(recordList,storedTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("玩家付费数据查询失败", e.getCause());
			throw new RuntimeException("玩家付费数据查询失败"); // 需要回滚事务
		}
	}
	
}
