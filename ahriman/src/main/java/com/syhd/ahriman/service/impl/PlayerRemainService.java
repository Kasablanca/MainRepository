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

import com.syhd.ahriman.dao.mapper.PlayerRemainMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.PlayerRemain;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.ArrayUtils;
import com.syhd.ahriman.utils.DateUtils;
import com.syhd.ahriman.web.controller.kpimonitor.PlayerRemainController;

@Service
@CacheConfig(cacheNames="playerRemain")
public class PlayerRemainService {

	private static final Logger logger = Logger.getLogger(PlayerRemainController.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private PlayerRemainMapper playerRemainMapper;
	
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
			// 若没给开始日期，则设置为上月同一天凌晨00:00:00
			copy.setStart(DateUtils.getOneWeekBeforeTime0());
		}
		if(StringUtils.isEmpty(copy.getEnd()))	{
			// 若没给结束日期，则设置为今天凌晨00:00:00.000
			copy.setEnd(DateUtils.getTodayTime0());
		} else {
			// 若已给截止日期，则判断是否超过今天凌晨00:00:00
			if(!copy.getEnd().before(DateUtils.getTodayTime0())) {
				// 说明 param.end >= 今天凌晨,则需要查询原始数据库和当前数据库的混合数据
				//return getMixinStatistic(copy);  暂时不查询今天的数据
			} else {
				// 说明在今天凌晨之前，则将截止日期设置为第二天凌晨
				Calendar now = Calendar.getInstance();
				now.setTime(copy.getEnd());
				now.add(Calendar.DAY_OF_MONTH, 1);
				copy.setEnd(now.getTime());
			}
		}
		
		TableData result = new TableData();
		List<PlayerRemain> list = playerRemainMapper.getStatistic(copy,pageAndSort);
		result.setData(list);
		result.setCode(0);
		result.setCount((long) playerRemainMapper.getStatisticCount(copy, pageAndSort));
		
		return result;
	}
	
	@CachePut
	@Transactional
	public int insert(PlayerRemain record) {
		return playerRemainMapper.insert(record);
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return playerRemainMapper.getAllPlatform();
	}
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	@Async
	public void async() {
		System.out.println("async:"+Thread.currentThread().getId());
	}
	
	/**
	 * 抓取每个日志服务器的活跃用户信息
	 * @param server 游戏日志服务器信息
	 */
	@Async
	@Transactional
	public void initFetch(AppServer server,Date yesterday,java.sql.Date endDate) {
		Date lastCountDate = playerRemainMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null || lastCountDate.compareTo(yesterday) <= 0) {
			// 如果昨天或者之前的日期没有统计，则需要在系统启动时进行统计
			String url = appServerService.getLogDbUrl(server.getLogDb());
			String user = gamelogProperties.getUsername();
			String password = gamelogProperties.getPassword();
			try(Connection conn = DriverManager.getConnection(url, user, password)) {	
				CallableStatement stmt = conn.prepareCall("call proc_get_player_retention(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				if(lastCountDate == null) {
					// 说明没任何统计，则统计截止今天凌晨00:00:00的数据
					stmt.setDate(1, null);
					stmt.setDate(2, endDate);
				} else {
					// 说明有统计，则判断统计信息是否已经最新
					PreparedStatement query = conn.prepareStatement("select count(*) from t_createplayerlog where date >= ?");
					
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
				List<PlayerRemain> recordList = new ArrayList<>(batchSize*2);
				while(resultSet.next()) {
					Date date = resultSet.getDate(1);
					String platform = resultSet.getString(2);
					BigDecimal day1 = resultSet.getBigDecimal(3);
					BigDecimal day2 = resultSet.getBigDecimal(4);
					BigDecimal day3 = resultSet.getBigDecimal(5);
					BigDecimal day4 = resultSet.getBigDecimal(6);
					BigDecimal day5 = resultSet.getBigDecimal(7);
					BigDecimal day6 = resultSet.getBigDecimal(8);
					BigDecimal day7 = resultSet.getBigDecimal(9);
					BigDecimal day14 = resultSet.getBigDecimal(10);
					BigDecimal day30 = resultSet.getBigDecimal(11);

					PlayerRemain record = new PlayerRemain();
					record.setDate(date);
					record.setPlatform(platform);
					record.setServerId(server.getServerid());
					record.setDay1(day1);
					record.setDay2(day2);
					record.setDay3(day3);
					record.setDay4(day4);
					record.setDay5(day5);
					record.setDay6(day6);
					record.setDay7(day7);
					record.setDay14(day14);
					record.setDay30(day30);
					recordList.add(record);
					
					if(recordList.size() == batchSize) {
						playerRemainMapper.batchInsert(recordList);
						recordList.clear();
					}
				}
				if(recordList.size() > 0) {
					playerRemainMapper.batchInsert(recordList);
				}
				
				stmt.close();
				stmt = conn.prepareCall("call proc_get_player_retention_for_update(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				//然后更新需要更新的字段
				Date earlyNotCountDate = playerRemainMapper.getEarlyDate();
				stmt.setDate(1, DateUtils.conver2SqlDate(earlyNotCountDate));
				stmt.setDate(2, DateUtils.conver2SqlDate(yesterday));
				
				resultSet = stmt.executeQuery();
				while(resultSet.next()) {
					Date date = resultSet.getDate(1);
					String platform = resultSet.getString(2);
					BigDecimal day1 = resultSet.getBigDecimal(3);
					BigDecimal day2 = resultSet.getBigDecimal(4);
					BigDecimal day3 = resultSet.getBigDecimal(5);
					BigDecimal day4 = resultSet.getBigDecimal(6);
					BigDecimal day5 = resultSet.getBigDecimal(7);
					BigDecimal day6 = resultSet.getBigDecimal(8);
					BigDecimal day7 = resultSet.getBigDecimal(9);
					BigDecimal day14 = resultSet.getBigDecimal(10);
					BigDecimal day30 = resultSet.getBigDecimal(11);
					
					PlayerRemain record = new PlayerRemain();
					record.setDate(date);
					record.setPlatform(platform);
					record.setServerId(server.getServerid());
					record.setDay1(day1);
					record.setDay2(day2);
					record.setDay3(day3);
					record.setDay4(day4);
					record.setDay5(day5);
					record.setDay6(day6);
					record.setDay7(day7);
					record.setDay14(day14);
					record.setDay30(day30);
					
					if(day2 != null || day3 != null || 
					   day4 != null || day5 != null || day6 != null || 
					   day7 != null || day14 != null || day30 != null) {
						playerRemainMapper.updateByUniqueKeySelective(record);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("角色留存初始查询失败", e.getCause());
				throw new RuntimeException("角色留存初始查询失败"); // 需要回滚事务
			}
		}
	}
	
	/**
	 * 初始化关于KPI玩家留存的预查询的定时任务,每天凌晨0点执行
	 */
	@Transactional
	@Scheduled(cron="0 0 0 * * ?")
	public void kpiActiveUserTask() {
		cacheManager.getCache("activeuser").clear(); // 手动清除缓存
		
		List<AppServer> serverList = appServerService.getAllServer();

		Date yesterday = DateUtils.getYesterdayTime0();
		java.sql.Date startDate = DateUtils.conver2SqlDate(yesterday);// 昨天凌晨00:00:00
		java.sql.Date endDate = DateUtils.conver2SqlDate(DateUtils.getTodayTime0()); // 当天凌晨00:00:00
		
		for(AppServer server : serverList) {
			Date lastCountDate = playerRemainMapper.getLastCountDate(server.getServerid());
			if(lastCountDate == null || lastCountDate.compareTo(yesterday) < 0) {
				// 说明昨天的或之前的数据还没统计
				String url = appServerService.getLogDbUrl(server.getLogDb());
				String user = gamelogProperties.getUsername();
				String password = gamelogProperties.getPassword();
				try(Connection conn = DriverManager.getConnection(url, user, password)) {
					CallableStatement stmt = conn.prepareCall("call proc_get_player_retention(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					
					stmt.setDate(1, startDate);
					stmt.setDate(2, endDate); 
					ResultSet resultSet = stmt.executeQuery();
					final int batchSize = 100; // 每次批量插入的阈值
					List<PlayerRemain> recordList = new ArrayList<>(batchSize*2);
					while(resultSet.next()) {
						Date date = resultSet.getDate(1);
						String platform = resultSet.getString(2);
						BigDecimal day1 = resultSet.getBigDecimal(3);
						BigDecimal day2 = resultSet.getBigDecimal(4);
						BigDecimal day3 = resultSet.getBigDecimal(5);
						BigDecimal day4 = resultSet.getBigDecimal(6);
						BigDecimal day5 = resultSet.getBigDecimal(7);
						BigDecimal day6 = resultSet.getBigDecimal(8);
						BigDecimal day7 = resultSet.getBigDecimal(9);
						BigDecimal day14 = resultSet.getBigDecimal(10);
						BigDecimal day30 = resultSet.getBigDecimal(11);

						PlayerRemain record = new PlayerRemain();
						record.setDate(date);
						record.setPlatform(platform);
						record.setServerId(server.getServerid());
						record.setDay1(day1);
						record.setDay2(day2);
						record.setDay3(day3);
						record.setDay4(day4);
						record.setDay5(day5);
						record.setDay6(day6);
						record.setDay7(day7);
						record.setDay14(day14);
						record.setDay30(day30);
						recordList.add(record);
						
						if(recordList.size() == batchSize) {
							playerRemainMapper.batchInsert(recordList);
							recordList.clear();
						}
					}
					if(recordList.size() > 0) {
						playerRemainMapper.batchInsert(recordList);
					}
					
					stmt.close();
					stmt = conn.prepareCall("call proc_get_player_retention_for_update(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					//然后更新需要更新的字段
					stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
					stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
					
					resultSet = stmt.executeQuery();
					while(resultSet.next()) {
						Date date = resultSet.getDate(1);
						String platform = resultSet.getString(2);
						BigDecimal day1 = resultSet.getBigDecimal(3);
						BigDecimal day2 = resultSet.getBigDecimal(4);
						BigDecimal day3 = resultSet.getBigDecimal(5);
						BigDecimal day4 = resultSet.getBigDecimal(6);
						BigDecimal day5 = resultSet.getBigDecimal(7);
						BigDecimal day6 = resultSet.getBigDecimal(8);
						BigDecimal day7 = resultSet.getBigDecimal(9);
						BigDecimal day14 = resultSet.getBigDecimal(10);
						BigDecimal day30 = resultSet.getBigDecimal(11);
						
						PlayerRemain record = new PlayerRemain();
						record.setDate(date);
						record.setPlatform(platform);
						record.setServerId(server.getServerid());
						record.setDay1(day1);
						record.setDay2(day2);
						record.setDay3(day3);
						record.setDay4(day4);
						record.setDay5(day5);
						record.setDay6(day6);
						record.setDay7(day7);
						record.setDay14(day14);
						record.setDay30(day30);
						
						if(day2 != null || day3 != null || 
							day4 != null || day5 != null || day6 != null || 
							day7 != null || day14 != null || day30 != null) {
									playerRemainMapper.updateByUniqueKeySelective(record);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("定时查询角色留存失败", e.getCause());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
					break;
				}
			}
		}
		System.gc();
	}
	
}
