package com.syhd.ahriman.service.impl;

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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syhd.ahriman.dao.mapper.CommonMapper;
import com.syhd.ahriman.dao.mapper.GeneralOnlineInfoMapper;
import com.syhd.ahriman.dao.model.AppServer;
import com.syhd.ahriman.dao.model.GeneralOnlineInfo;
import com.syhd.ahriman.dto.GeneralOnlineInfoVO;
import com.syhd.ahriman.dto.PageAndSort;
import com.syhd.ahriman.dto.RequestPayload;
import com.syhd.ahriman.dto.TableData;
import com.syhd.ahriman.properties.GamelogProperties;
import com.syhd.ahriman.utils.DateUtils;

@Service
@CacheConfig(cacheNames="generalOnlineInfo")
public class GeneralOnlineInfoService {

	private static final Logger logger = Logger.getLogger(GeneralOnlineInfoService.class);
	
	@Autowired
	private AppServerService appServerService;
	
	@Autowired
	private GamelogProperties gamelogProperties;
	
	@Autowired
	private GeneralOnlineInfoMapper generalOnlineInfoMapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	/**
	 * 获取综合在线数据
	 * @param param 查询参数
	 * @return 包含汇总数据
	 */
	@Cacheable(sync=true)
	public TableData getStatistic(RequestPayload param,PageAndSort pageAndSort) {
		RequestPayload copy = RequestPayload.prepare(param,null,null);
		
		TableData result = new TableData();
		result.setCode(0);
		
		List<GeneralOnlineInfoVO> list;
		Long count;
		
		if(copy.getServerId() == null) {
			commonMapper.insertTDate(copy.getStart(), copy.getEnd());
			list = generalOnlineInfoMapper.getStatisticWithUserRegistered(copy,pageAndSort);
			count = generalOnlineInfoMapper.getStatisticCountWithUserRegistered(copy, pageAndSort);
		} else {
			list = generalOnlineInfoMapper.getStatistic(copy,pageAndSort);
			count = generalOnlineInfoMapper.getStatisticCount(copy, pageAndSort);
		}
		result.setData(list);
		result.setCount(count);
		result.setExtra(RequestPayload.unPrepare(copy));
		
		return result;
	}
	
	@Cacheable(key="#root.method")
	public List<String> getAllPlatform() {
		return generalOnlineInfoMapper.getAllPlatform();
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
	@CacheEvict(allEntries=true)
	public void task() {
		List<AppServer> serverList = appServerService.getAllServer();
		for(AppServer server : serverList) {
			generalOnlineInfoTask(server);
		}
	}
	
	/**
	 * 抓取每个日志服务器的综合数据-综合在线数据信息
	 * @param server 游戏日志服务器信息
	 */
	public void generalOnlineInfoTask(AppServer server) {
		Date startDate = null;
		Date endDate = DateUtils.getTodayTime0();
		Date lastCountDate = generalOnlineInfoMapper.getLastCountDate(server.getServerid());
		if(lastCountDate == null) {
			startDate = new Date(0);
		} else {
			Calendar now = Calendar.getInstance();
			now.setTime(lastCountDate);
			now.add(Calendar.DAY_OF_MONTH, 1);
			startDate = now.getTime();
		}
		
		doGeneralOnlineInfoTask(startDate, endDate, server, "t_general_online_info");
	}
	
	private void doGeneralOnlineInfoTask(Date startDate, Date endDate, AppServer server, String storedTable) {
		if(!startDate.before(endDate)) {
			// 如果开始日期没在结束日期之前，则不用处理
			return;
		}
		
		String url = appServerService.getLogDbUrl(server.getLogDb());
		String user = gamelogProperties.getUsername();
		String password = gamelogProperties.getPassword();
		try(Connection conn = DriverManager.getConnection(url, user, password)) {	
			CallableStatement stmt = conn.prepareCall("call proc_get_general_online_info(?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setDate(1, DateUtils.conver2SqlDate(startDate));
			stmt.setDate(2, DateUtils.conver2SqlDate(endDate));
			
			ResultSet resultSet = stmt.executeQuery();
			final int batchSize = 100; // 每次批量插入的阈值
			List<GeneralOnlineInfo> recordList = new ArrayList<>(batchSize*2);
			while(resultSet.next()) {
				Date date = resultSet.getDate(1);
				String platform = resultSet.getString(2);
				Long totalDuration = resultSet.getLong(3);
				Integer maxOnline = resultSet.getInt(4);
				Integer liveness = resultSet.getInt(5);
				Integer loginCount = resultSet.getInt(6);
				Integer newPlayerCount = resultSet.getInt(7);
				Integer backUserCount = resultSet.getInt(8);
				Integer totalDurationNew = resultSet.getInt(9);

				GeneralOnlineInfo record = new GeneralOnlineInfo();
				record.setDate(date);
				record.setPlatform(platform);
				record.setServerId(server.getServerid());
				record.setBackUserCount(backUserCount);
				record.setLiveness(liveness);
				record.setLoginCount(loginCount);
				record.setMaxOnline(maxOnline);
				record.setNewPlayerCount(newPlayerCount);
				record.setTotalDuration(totalDuration);
				record.setTotalDurationNew(totalDurationNew);
				
				recordList.add(record);
				
				if(recordList.size() == batchSize) {
					generalOnlineInfoMapper.batchInsert(recordList,storedTable);
					recordList.clear();
				}
			}
			if(recordList.size() > 0) {
				generalOnlineInfoMapper.batchInsert(recordList,storedTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("综合数据-综合在线数据初始查询失败", e.getCause());
			throw new RuntimeException("综合数据-综合在线数据初始查询失败"); // 需要回滚事务
		}
	}
	
}
