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

import com.syhd.ahriman.dao.mapper.KpiUserLtvMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.KpiUserLtv;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.ArrayUtils;
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
	private CacheManager cacheManager;
	
	@Autowired
	private KpiUserLtvMapper kpiUserLtvMapper;
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
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
				return getMixinStatistic(copy,pageAndSort);
			} else {
				// 说明在今天凌晨之前，则将截止日期设置为第二天凌晨
				Calendar now = Calendar.getInstance();
				now.setTime(copy.getEnd());
				now.add(Calendar.DAY_OF_MONTH, 1);
				copy.setEnd(now.getTime());
			}
		}
		
		TableData result = new TableData();
		List<KpiUserLtv> list = kpiUserLtvMapper.getStatistic(copy,pageAndSort);
		result.setData(list);
		result.setCode(0);
		result.setCount(kpiUserLtvMapper.getStatisticCount(copy, pageAndSort));
		
		return result;
	}
	
	@CachePut
	@Transactional
	public int insert(KpiUserLtv record) {
		return kpiUserLtvMapper.insert(record);
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return kpiUserLtvMapper.getAllPlatform();
	}
	
/*====================================分割线,以下方法非对外使用====================================*/
	
	/**
	 * 查询原始数据库和当前数据库的混合数据
	 * @param param 查询参数
	 * @param pageAndSort 分页和排序参数
	 * @return 查询结果或失败信息
	 */
	@Transactional
	public TableData getMixinStatistic(RequestPayload param,PageAndSort pageAndSort) {
		TableData result = new TableData();
		
		Result temp = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(temp)) {
			result.setCode(-1);
			result.setMsg(temp.getMessage());
			logger.error("混合查询付费数据失败，原因："+temp.getMessage());
		}
		ExchangeRate rate = (ExchangeRate) temp.getData();// 汇率
		
		List<AppServer> serverList = appServerService.getAllServer();
		
		for(AppServer server : serverList) {
			String url = appServerService.getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {
				// 从日志数据库查询从今天凌晨开始的数据，并存入系统数据库的临时表
				CallableStatement stmt = conn.prepareCall("call proc_get_user_ltv(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setDate(1, DateUtils.conver2SqlDate(DateUtils.getTodayTime0()));
				stmt.setDate(2, null);
				stmt.setBigDecimal(3, rate.getUsd());
				stmt.setBigDecimal(4, rate.getTwd());
				stmt.setBigDecimal(5, rate.getHkd());
				
				ResultSet resultSet = stmt.executeQuery();
				
				final int batchSize = 100; // 每次批量插入的阈值
				List<KpiUserLtv> recordList = new ArrayList<>(batchSize*2);
				kpiUserLtvMapper.createTodayKpiUserLtvTable();
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
						kpiUserLtvMapper.batchInsertTemp(recordList);
						recordList.clear();
					}
				}
				if(recordList.size() > 0) {
					kpiUserLtvMapper.batchInsertTemp(recordList);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("实时查询用户价值（LTV）数据失败", e.getCause());
				result.setCode(-1);
				result.setMsg("实时查询用户价值（LTV）数据失败");
				return result;
			}
		}
		
		
		List<KpiUserLtv> list = kpiUserLtvMapper.getMixinStatistic(param,pageAndSort);
		result.setData(list);
		result.setCode(0);
		result.setCount(kpiUserLtvMapper.getMixinStatisticCount(param, pageAndSort));
		
		return result;
	}
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	/**
	 * 抓取每个日志服务器的用户价值（LTV）信息
	 * @param server 游戏日志服务器信息
	 */
	@Async
	@Transactional
	public void initFetch(AppServer server,Date yesterday,java.sql.Date endDate) {
		Result result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			logger.error("用户价值（LTV）初始查询失败，原因："+result.getMessage());
			return;
		}
		ExchangeRate rate = (ExchangeRate) result.getData();// 汇率
		
		Date lastCountDate = kpiUserLtvMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null || lastCountDate.compareTo(yesterday) <= 0) {
			// 如果昨天或者之前的日期没有统计，则需要在系统启动时进行统计
			String url = appServerService.getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {	
				CallableStatement stmt = conn.prepareCall("call proc_get_user_ltv(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setBigDecimal(3, rate.getUsd());
				stmt.setBigDecimal(4, rate.getTwd());
				stmt.setBigDecimal(5, rate.getHkd());
				
				if(lastCountDate == null) {
					// 说明没任何统计，则统计截止今天凌晨00:00:00的数据
					stmt.setDate(1, null);
					stmt.setDate(2, endDate);
				} else {
					// 说明有统计，则判断统计信息是否已经最新
					PreparedStatement query = conn.prepareStatement("select count(*) from (SELECT pid,date(MIN(date)) date FROM t_loginlog GROUP BY pid) t where date >= ?");
					
					Calendar now = Calendar.getInstance();
					now.setTime(lastCountDate);
					now.add(Calendar.DAY_OF_MONTH, 1);
					java.sql.Date critical = DateUtils.conver2SqlDate(now.getTime());
					query.setDate(1, critical);
					
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
						kpiUserLtvMapper.batchInsert(recordList);
						recordList.clear();
					}
				}
				if(recordList.size() > 0) {
					kpiUserLtvMapper.batchInsert(recordList);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("用户价值（LTV）初始查询失败", e.getCause());
				throw new RuntimeException("用户价值（LTV）初始查询失败"); // 需要回滚事务
			}
		}
	}
	
	/**
	 * 初始化关于用户价值（LTV）的预查询的定时任务,每天凌晨0点执行
	 */
	@Transactional
	@Scheduled(cron="0 0 0 * * ?")
	public void kpiActiveUserTask() {
		cacheManager.getCache("userltv").clear(); // 手动清除缓存
		
		Result result = exchangeRateService.getExchangeRate();
		if(!Result.isSuccessResult(result)) {
			result.setMessage(result.getMessage());
			logger.error("用户价值（LTV）初始查询失败，原因："+result.getMessage());
			return;
		}
		ExchangeRate rate = (ExchangeRate) result.getData();// 汇率
		
		List<AppServer> serverList = appServerService.getAllServer();

		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date startDate = DateUtils.conver2SqlDate(yesterday);// 昨天凌晨00:00:00
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		
		for(AppServer server : serverList) {
			Date lastCountDate = kpiUserLtvMapper.getLastCountDate(server.getServerid());
			if(lastCountDate == null || lastCountDate.compareTo(yesterday) < 0) {
				// 说明昨天的或之前的数据还没统计
				String url = appServerService.getLogDbUrl(server.getLogDb());
				String user = gamelogProperties.getUsername();
				String password = gamelogProperties.getPassword();
				try(Connection conn = DriverManager.getConnection(url, user, password)) {
					CallableStatement stmt = conn.prepareCall("call proc_get_user_ltv(?,?,?,?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setBigDecimal(3, rate.getUsd());
					stmt.setBigDecimal(4, rate.getTwd());
					stmt.setBigDecimal(5, rate.getHkd());
					
					stmt.setDate(1, startDate);
					stmt.setDate(2, endDate); 
					ResultSet resultSet = stmt.executeQuery();
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
							kpiUserLtvMapper.batchInsert(recordList);
							recordList.clear();
						}
					}
					if(recordList.size() > 0) {
						kpiUserLtvMapper.batchInsert(recordList);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("定时查询用户价值（LTV）数据失败", e.getCause());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
					break;
				}
			}
		}
		System.gc();
	}
	
}
