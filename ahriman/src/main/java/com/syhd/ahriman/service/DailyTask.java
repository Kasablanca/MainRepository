package com.syhd.ahriman.service;

/**
 * 每日任务接口
 * @author MIN.LEE
 *
 */
public interface DailyTask {

	/**
	 * 每次项目启动时和每天0点都会被异步执行，并会应用默认事务
	 */
	void run();
	
}
