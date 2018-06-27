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

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.KpiUserLtvMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.KpiUserLtv;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.service.CronTask;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="userltv")
public class KpiUserLTVService {

private static final Logger logger = Logger.getLogger(KpiUserLTVService.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private KpiUserLtvMapper kpiUserLtvMapper;
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	/**
	 * 获取KPI活跃用户
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,null,null);
		
		List<KpiUserLtv> list;
		Long count;
		
		if(RequestPayload.containToday(copy)) {
			//查询期限包含今天
			doRealtimeStatistic();
			list = kpiUserLtvMapper.getMixinStatistic(copy,pageAndSort);
			count = kpiUserLtvMapper.getMixinStatisticCount(copy, pageAndSort);
		} else {
			//查询期限不包含今天
			list = kpiUserLtvMapper.getStatistic(copy,pageAndSort);
			count = kpiUserLtvMapper.getStatisticCount(copy, pageAndSort);
		}
		
		list = KpiUserLtv.fill(list, copy.getStart(), copy.getEnd());
		
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
		
		kpiUserLtvMapper.createTodayKpiUserLtvTable();
		for(AppServer server : serverList) {
			doKpiUserLtvTask(startDate,endDate,server,"t_today_kpi_user_ltv");
		}
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return kpiUserLtvMapper.getAllPlatform();
	}
	
/*====================================分割线,以下方法非对外使用====================================*/
	
	@CronTask("0 0 0 * * ?")
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			kpiUserLtvTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的用户LTV数据信息
	 * @param server 游戏日志服务器信息
	 */
	public void kpiUserLtvTask(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		Date lastCountDate = kpiUserLtvMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doKpiUserLtvTask(startDate, endDate, server, "t_kpi_user_ltv");
	}
	
	private void doKpiUserLtvTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		Result temp = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(temp)) {
			logger.error("混合查询用户LTV数据失败，原因："+temp.getMessage());
			throw new RuntimeException("混合查询用户LTV数据失败，原因"+temp.getMessage());
		}
		ExchangeRate rate = (ExchangeRate) temp.getData();// 汇率
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			CallableStatement stmt = conn.prepareCall("call proc_get_user_ltv(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			stmt.setBigDecimal(3, rate.getUsd());
			stmt.setBigDecimal(4, rate.getTwd());
			stmt.setBigDecimal(5, rate.getHkd());
			
			ResultSet resultSet = stmt.executeQuery();
			resultSet.setFetchSize(100);
			final int batchSize = 100; // 每次批量插入的阈值
			List<KpiUserLtv> recordList = new ArrayList<>(batchSize*2);
			while(resultSet.next()) {
				String platform = resultSet.getString(1);
				Date date = resultSet.getDate(2);
				Integer newUserNumber = resultSet.getInt(3);
				BigDecimal totalMoney = resultSet.getBigDecimal(4);
				BigDecimal day1 = resultSet.getBigDecimal(5);
				BigDecimal day2 = resultSet.getBigDecimal(6);
				BigDecimal day3 = resultSet.getBigDecimal(7);
				BigDecimal day4 = resultSet.getBigDecimal(8);
				BigDecimal day5 = resultSet.getBigDecimal(9);
				BigDecimal day6 = resultSet.getBigDecimal(10);
				BigDecimal day7 = resultSet.getBigDecimal(11);
				BigDecimal day8 = resultSet.getBigDecimal(12);
				BigDecimal day9 = resultSet.getBigDecimal(13);
				BigDecimal day10 = resultSet.getBigDecimal(14);
				BigDecimal day11 = resultSet.getBigDecimal(15);
				BigDecimal day12 = resultSet.getBigDecimal(16);
				BigDecimal day13 = resultSet.getBigDecimal(17);
				BigDecimal day14 = resultSet.getBigDecimal(18);
				BigDecimal day15 = resultSet.getBigDecimal(19);
				BigDecimal day16 = resultSet.getBigDecimal(20);
				BigDecimal day17 = resultSet.getBigDecimal(21);
				BigDecimal day18 = resultSet.getBigDecimal(22);
				BigDecimal day19 = resultSet.getBigDecimal(23);
				BigDecimal day20 = resultSet.getBigDecimal(24);
				BigDecimal day21 = resultSet.getBigDecimal(25);
				BigDecimal day22 = resultSet.getBigDecimal(26);
				BigDecimal day23 = resultSet.getBigDecimal(27);
				BigDecimal day24 = resultSet.getBigDecimal(28);
				BigDecimal day25 = resultSet.getBigDecimal(29);
				BigDecimal day26 = resultSet.getBigDecimal(30);
				BigDecimal day27 = resultSet.getBigDecimal(31);
				BigDecimal day28 = resultSet.getBigDecimal(32);
				BigDecimal day29 = resultSet.getBigDecimal(33);
				BigDecimal day30 = resultSet.getBigDecimal(34);
				

				KpiUserLtv record = new KpiUserLtv();
				record.setDate(date);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setNewUserNumber(newUserNumber);
				record.setTotalMoney(totalMoney);
				record.setDay1(day1);
				record.setDay2(day2);
				record.setDay3(day3);
				record.setDay4(day4);
				record.setDay5(day5);
				record.setDay6(day6);
				record.setDay7(day7);
				record.setDay8(day8);
				record.setDay9(day9);
				record.setDay10(day10);
				record.setDay11(day11);
				record.setDay12(day12);
				record.setDay13(day13);
				record.setDay14(day14);
				record.setDay15(day15);
				record.setDay16(day16);
				record.setDay17(day17);
				record.setDay18(day18);
				record.setDay19(day19);
				record.setDay20(day20);
				record.setDay21(day21);
				record.setDay22(day22);
				record.setDay23(day23);
				record.setDay24(day24);
				record.setDay25(day25);
				record.setDay26(day26);
				record.setDay27(day27);
				record.setDay28(day28);
				record.setDay29(day29);
				record.setDay30(day30);
				
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					kpiUserLtvMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				kpiUserLtvMapper.batchInsert(recordList,storedTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户LTV数据初始查询失败", e.getCause());
			throw new RuntimeException("用户LTV数据初始查询失败"); // 需要回滚事务
		}
	}
	
}
