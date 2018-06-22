package com.syhd.ahriman.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.syhd.ahriman.dao.mapper.NewValidUserMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.NewValidUser;
import com.syhd.ahriman.dto.NewValidUserVO;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.service.DailyTask;
import com.syhd.ahriman.utils.DateUtils;
import com.syhd.ahriman.utils.DateUtils.DateUnit;

@Service
@CacheConfig(cacheNames="newValidUser")
public class NewValidUserService implements DailyTask {

	private static final Logger logger = Logger.getLogger(NewValidUserService.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private NewValidUserMapper newValidUserMapper;
	
	/**
	 * 获取新增有效用户数据
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,null,null);
		
		TableData result = new TableData();
		result.setCode(0);
		
		List<NewValidUserVO> list;
		Long count;
		
		if(RequestPayload.containToday(copy)) {
			//查询期限包含今天
			doRealtimeStatistic();
			
			Calendar now = Calendar.getInstance();
			Date lastCountDate = newValidUserMapper.getLastCountDate(null);
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, -7);
			
			RequestPayload temp = new RequestPayload(copy);
			temp.setEnd(now.getTime());//主表的查询截止日期设置为服务器最后一次统计日期的7天前
			
			list = newValidUserMapper.getMixinStatistic(temp,pageAndSort);
			count = newValidUserMapper.getMixinStatisticCount(temp, pageAndSort);
		} else {
			//查询期限不包含今天
			list = newValidUserMapper.getStatistic(copy,pageAndSort);
			count = newValidUserMapper.getStatisticCount(copy, pageAndSort);
		}
		
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
		
		Calendar now = Calendar.getInstance();
		
		Date lastCountDate = newValidUserMapper.getLastCountDate(null);
		now.setTime(lastCountDate);
		now.add(Calendar.DAY_OF_MONTH, -7);
		Date startDate = now.getTime();//开始日期设置为服务器最后一次统计日期的7天前
		
		now.setTime(DateUtils.getTodayTime0());
		now.add(Calendar.DAY_OF_MONTH, 1);
		Date endDate = now.getTime(); //截止日期为明天凌晨0点
		
		newValidUserMapper.createTempTable();
		for(AppServer server : serverList) {
			doTask(startDate,endDate,server,"t_today_new_valid_user");
		}
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return newValidUserMapper.getAllPlatform();
	}
	
	/*====================================分割线,以下方法非对外使用====================================*/
	
	@Override
	@CacheEvict(allEntries=true)
	public void run() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			task(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的新增有效用户数据
	 * @param server 游戏日志服务器信息
	 */
	public void task(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		Date lastCountDate = newValidUserMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doTask(startDate, endDate, server, "t_new_valid_user");
	}
	
	private void doTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			CallableStatement stmt = conn.prepareCall("call proc_get_new_valid_user(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			ResultSet resultSet = stmt.executeQuery();
			final int batchSize = 100; // 每次批量插入的阈值
			List<NewValidUser> recordList = new ArrayList<>(batchSize*2);
			while(resultSet.next()) {
				NewValidUser record = resultMap(resultSet);
				record.setServerId(server.getServerid());
				
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					newValidUserMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				newValidUserMapper.batchInsert(recordList,storedTable);
			}
			
			//更新需要更新的记录
			endDate = startDate; // 结束日期设置为开始日期
			startDate = DateUtils.dateAdd(startDate, DateUnit.DAY, -7);//将开始日期重新设置为一周以前
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				NewValidUser record = resultMap(resultSet);
				record.setServerId(server.getServerid());
				
				if(record.getDisposableCount() != null || 
				   record.getCommonlyCount() != null || 
				   record.getLoyaltyCount() != null) {
					newValidUserMapper.updateByUniqueKeySelective(record);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增有效用户数据查询失败", e.getCause());
			throw new RuntimeException("新增有效用户数据查询失败"); // 需要回滚事务
		}
	}
	
	private NewValidUser resultMap(ResultSet resultSet) throws SQLException {
		Date date = resultSet.getDate(1);
		String platform = resultSet.getString(2);
		Integer newUserCount = resultSet.getInt(3);
		Integer disposableCount = resultSet.getInt(4);
		Integer commonlyCount = resultSet.getInt(5);
		Integer loyaltyCount = resultSet.getInt(6);

		NewValidUser result = new NewValidUser();
		result.setDate(date);
		result.setPlatform(platform);
		result.setCommonlyCount(commonlyCount);
		result.setDisposableCount(disposableCount);
		result.setLoyaltyCount(loyaltyCount);
		result.setNewUserCount(newUserCount);
		
		return result;
	}

}
