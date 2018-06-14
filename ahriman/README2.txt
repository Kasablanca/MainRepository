1.主数据库创建表，脚本如下：
CREATE TABLE t_kpi_pay  (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `server_id` int(11) NULL DEFAULT NULL,
  `platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `active_user_number` int(11) NULL DEFAULT NULL,
  `revenue` decimal(20, 0) NULL DEFAULT NULL,
  `pay_user_number` int(11) NULL DEFAULT NULL,
  `old_player_number` int(11) NULL DEFAULT NULL,
  `old_pay_revenue` decimal(20, 0) NULL DEFAULT NULL,
  `old_pay_number` int(11) NULL DEFAULT NULL,
  `new_player_number` int(11) NULL DEFAULT NULL,
  `new_pay_revenue` decimal(20, 0) NULL DEFAULT NULL,
  `new_pay_number` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_kpi_pay_unionkey`(`date`, `server_id`, `platform`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

CREATE t_player_remain  (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `server_id` int(11) NULL DEFAULT NULL,
  `platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `day1` decimal(20, 0) NULL DEFAULT NULL,
  `day2` decimal(20, 0) NULL DEFAULT NULL,
  `day3` decimal(20, 0) NULL DEFAULT NULL,
  `day4` decimal(20, 0) NULL DEFAULT NULL,
  `day5` decimal(20, 0) NULL DEFAULT NULL,
  `day6` decimal(20, 0) NULL DEFAULT NULL,
  `day7` decimal(20, 0) NULL DEFAULT NULL,
  `day14` decimal(20, 0) NULL DEFAULT NULL,
  `day30` decimal(20, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

2.主数据库创建存储过程，脚本如下：
CREATE PROCEDURE `create_temp_kpi_pay_table`()
BEGIN
	DROP TEMPORARY TABLE IF EXISTS t_today_kpi_pay; #由于连接池的存在，需要在创建之前删除之前创建的临时表
	CREATE TEMPORARY TABLE `t_today_kpi_pay` (
		  `date` date NOT NULL,
			`server_id` int(11) NULL DEFAULT NULL,
			`platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
			`active_user_number` int(11) NULL DEFAULT NULL,
			`revenue` decimal(20, 0) NULL DEFAULT NULL,
			`pay_user_number` int(11) NULL DEFAULT NULL,
			`old_player_number` int(11) NULL DEFAULT NULL,
			`old_pay_revenue` decimal(20, 0) NULL DEFAULT NULL,
			`old_pay_number` int(11) NULL DEFAULT NULL,
			`new_player_number` int(11) NULL DEFAULT NULL,
			`new_pay_revenue` decimal(20, 0) NULL DEFAULT NULL,
			`new_pay_number` int(11) NULL DEFAULT NULL,
			UNIQUE INDEX `uk_t_kpi_pay_unionkey`(`date`, `server_id`, `platform`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
END

3.日志数据库创建存储过程，脚本如下：
CREATE PROCEDURE `proc_get_pay_kpi`(IN `startDate` date,IN `endDate` date,IN `usd` decimal,IN `twd` decimal,IN `hkd` decimal)
BEGIN
IF(startDate IS NULL) THEN SET startDate = 0; END IF; #如果没给开始日期，则设置为0
IF(endDate IS NULL) THEN SET endDate = DATE_ADD(DATE(now()),INTERVAL 1 DAY); END IF;#如果没有给结束日期，则设置为明天凌晨0点
SELECT
	t_active_user.date,
	t_active_user.platform,
	t_active_user.count as active_user_number, #活跃用户
	t_revenue.money as revenue, #每日收入
	t_pay_user.count as pay_user_number, #付费用户数
#	t_revenue.money/t_pay_user.count as pay_arppu, #付费ARPPU
#	t_pay_user.count/t_active_user.count as pay_rate, #付费率
#	t_revenue.money/t_active_user.count as active_arpu, #活跃ARPU
	t_new_role.new_player_number, #新增角色数
	t_new_pay.new_pay_revenue, #新增付费额
	t_new_pay.new_pay_number, #新增付费人数
#	t_new_pay.new_pay_revenue/t_new_pay.new_pay_number as pay_arppu_new, #新增ARPPU
#	t_new_pay.new_pay_number/t_new_role.new_player_number as pay_rate_new, #首日付费转化率
#	t_new_pay.new_pay_revenue/t_new_role.new_player_number as active_arpu_new, #新增ARPU
	t_active_user.count - t_new_role.new_player_number as old_player_number, #老玩家数量
	t_revenue.money - t_new_pay.new_pay_revenue as old_pay_revenue, #老玩家付费额
	t_pay_user.count - t_new_pay.new_pay_number as old_pay_number #老玩家付费人数
#	(t_revenue.money - t_new_pay.new_pay_revenue) / (t_pay_user.count - t_new_pay.new_pay_number) as pay_arppu_old, #老玩家ARPPU
#	(t_pay_user.count - t_new_pay.new_pay_number) / (t_active_user.count - t_new_role.new_player_number) as pay_rate_old, #老玩家付费率
#	(t_revenue.money - t_new_pay.new_pay_revenue) / (t_active_user.count - t_new_role.new_player_number) as active_arpu_old #老玩家ARPU
#活跃用户
FROM (
	select date,platform,count(pid) count 
	from (select distinct date(t1.date) date,t2.platform,t1.pid from t_loginlog t1 inner join t_createplayerlog t2 on t1.pid = t2.pid where t1.date >= startDate and t1.date < endDate and t1.flag = 1) t 
	group by date,platform
) t_active_user
# 每日收入
LEFT JOIN (
	select date,platform,sum(money) money 
	from (select date(date) date,platform,rmb*case lv when 0 then 1 when 1 then usd when 2 then twd when 3 then hkd end money from t_rechargelog where date >= startDate and date < endDate ) t
	group by date,platform
) t_revenue ON t_active_user.date = t_revenue.date AND t_active_user.platform = t_revenue.platform
# 每日付费用户数
LEFT JOIN (
	SELECT t2.platform,t1.date,count( t1.pid ) count 
	FROM ( SELECT DISTINCT date( date ) date, pid FROM t_rechargelog WHERE date >= startDate AND date < endDate ) t1 
	INNER JOIN t_createplayerlog t2 ON t1.pid = t2.pid GROUP BY t2.platform,t1.date
) t_pay_user ON t_active_user.date = t_pay_user.date AND t_active_user.platform = t_pay_user.platform
# 新增角色数
LEFT JOIN (
	SELECT t2.platform,t1.date,count(t1.pid) new_player_number
	FROM (SELECT pid,date(MIN(date)) date FROM t_loginlog WHERE date >= startDate AND date < endDate GROUP BY pid) t1 
	INNER JOIN t_createplayerlog t2 ON t1.pid = t2.pid 
	GROUP BY t1.date,t2.platform
) t_new_role ON t_active_user.date = t_new_role.date AND t_active_user.platform = t_new_role.platform
# 新增付费额和付费人数
LEFT JOIN (
	SELECT t2.platform,t1.date, sum(t2.rmb*case t2.lv when 0 then 1 when 1 then usd when 2 then twd when 3 then hkd end) new_pay_revenue,count(t1.pid) new_pay_number
	FROM (SELECT pid,date(MIN(date)) date FROM t_loginlog WHERE date >= startDate AND date < endDate GROUP BY pid) t1 
	INNER JOIN t_rechargelog t2 ON t1.pid = t2.pid AND t1.date = date(t2.date) 
	GROUP BY t2.platform,t1.date
) t_new_pay ON t_active_user.date = t_new_pay.date AND t_active_user.platform = t_new_pay.platform;
END

=========================================================================
CREATE PROCEDURE `proc_get_player_retention`(IN `startDate` date,IN `endDate` date)
BEGIN
IF(startDate IS NULL) THEN SET startDate = 0; END IF;#如果没给开始日期，则设置为0
IF(endDate IS NULL) THEN SET endDate = DATE_ADD(DATE(now()),INTERVAL 1 DAY); END IF;#如果没有给结束日期，则设置为明天凌晨0点
SELECT
	tt1.date,tt1.platform,
	tt1.day1,
	tt2.dayn day2,
	tt3.dayn day3,
	tt4.dayn day4,
	tt5.dayn day5,
	tt6.dayn day6,
	tt7.dayn day7,
	tt14.dayn day14,
	tt30.dayn day30
FROM (
	SELECT date,platform,count( pid ) day1 
	FROM ( SELECT date( date ) date, platform, pid FROM t_createplayerlog WHERE date >= startDate AND date <= endDate ) t1 
	GROUP BY date,platform
)tt1 
LEFT JOIN(
	SELECT regdate,date,platform,count( pid ) dayn
	FROM(	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate 
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid 
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 1 AND t2.date >= startDate AND t2.date <=endDate
			) t 
	GROUP BY regdate,date,platform
) tt2 ON tt1.date = tt2.regdate AND tt1.platform = tt2.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM( SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 2 AND t2.date >= startDate AND t2.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt3 ON tt1.date = tt3.regdate AND tt1.platform = tt3.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM(	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid, date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 3 AND t2.date >= startDate AND t2.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt4 ON tt1.date = tt4.regdate AND tt1.platform = tt4.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
					FROM t_loginlog t1
					LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
					WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 4 AND t2.date >= startDate AND t2.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt5 ON tt1.date = tt5.regdate AND tt1.platform = tt5.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
					FROM t_loginlog t1
					LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
					WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 5 AND t2.date >= startDate AND t2.date <=endDate
			) t 
	GROUP BY regdate,date,platform
) tt6 ON tt1.date = tt6.regdate AND tt1.platform = tt6.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
					FROM t_loginlog t1
					LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
					WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 6 AND t2.date >= startDate AND t2.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt7 ON tt1.date = tt7.regdate AND tt1.platform = tt7.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 13 AND t2.date >= startDate AND t2.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt14 ON tt1.date = tt14.regdate AND tt1.platform = tt14.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 29 AND t2.date >= startDate AND t2.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt30 ON tt1.date = tt30.regdate AND tt1.platform = tt30.platform;
END
=========================================================================
CREATE PROCEDURE `proc_get_player_retention_for_update`(IN `startDate` date,IN `endDate` date)
BEGIN
IF(startDate IS NULL) THEN SET startDate = 0; END IF;#如果没给开始日期，则设置为0
IF(endDate IS NULL) THEN SET endDate = DATE_ADD(DATE(now()),INTERVAL 1 DAY); END IF;#如果没有给结束日期，则设置为明天凌晨0点
SELECT
	tt1.date,tt1.platform,
	tt1.day1,
	tt2.dayn day2,
	tt3.dayn day3,
	tt4.dayn day4,
	tt5.dayn day5,
	tt6.dayn day6,
	tt7.dayn day7,
	tt14.dayn day14,
	tt30.dayn day30
FROM (
	SELECT date,platform,count( pid ) day1 
	# 只需查询注册日期在最早的登录日期30天之后以及最迟登录日期之前
	FROM ( SELECT date( date ) date, platform, pid FROM t_createplayerlog WHERE date >= DATE_SUB(startDate,INTERVAL 30 DAY) AND date <= endDate ) t1
	GROUP BY date,platform
)tt1 
LEFT JOIN(
	SELECT regdate,date,platform,count( pid ) dayn
	FROM(	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate 
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid 
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 1 AND t1.date >= startDate AND t1.date <=endDate
			) t 
	GROUP BY regdate,date,platform
) tt2 ON tt1.date = tt2.regdate AND tt1.platform = tt2.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM( SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 2 AND t1.date >= startDate AND t1.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt3 ON tt1.date = tt3.regdate AND tt1.platform = tt3.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM(	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid, date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 3 AND t1.date >= startDate AND t1.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt4 ON tt1.date = tt4.regdate AND tt1.platform = tt4.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
					FROM t_loginlog t1
					LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
					WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 4 AND t1.date >= startDate AND t1.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt5 ON tt1.date = tt5.regdate AND tt1.platform = tt5.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
					FROM t_loginlog t1
					LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
					WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 5 AND t1.date >= startDate AND t1.date <=endDate
			) t 
	GROUP BY regdate,date,platform
) tt6 ON tt1.date = tt6.regdate AND tt1.platform = tt6.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (	SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
					FROM t_loginlog t1
					LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
					WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 6 AND t1.date >= startDate AND t1.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt7 ON tt1.date = tt7.regdate AND tt1.platform = tt7.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 13 AND t1.date >= startDate AND t1.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt14 ON tt1.date = tt14.regdate AND tt1.platform = tt14.platform
LEFT JOIN (
	SELECT regdate,date,platform,count( pid ) dayn
	FROM (SELECT DISTINCT date( t1.date ) date,t2.platform,t1.pid,date(t2.date) regdate
				FROM t_loginlog t1
				LEFT JOIN t_createplayerlog t2 ON t1.pid = t2.pid
				WHERE t1.flag = 1 AND DATEDIFF(t1.date,t2.date) = 29 AND t1.date >= startDate AND t1.date <=endDate
		) t 
	GROUP BY regdate,date,platform
) tt30 ON tt1.date = tt30.regdate AND tt1.platform = tt30.platform;
END