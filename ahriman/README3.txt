1.主数据库创建表，脚本如下：
CREATE TABLE `loginserver1001`.`t_kpi_user_ltv`  (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `date` date NULL DEFAULT NULL,
  `server_id` int(11) NULL DEFAULT NULL,
  `platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `new_user_number` int(11) NULL DEFAULT NULL,
  `total_money` decimal(16, 0) NULL DEFAULT NULL,
  `day1` decimal(16, 0) NULL DEFAULT NULL,
  `day2` decimal(16, 0) NULL DEFAULT NULL,
  `day3` decimal(16, 0) NULL DEFAULT NULL,
  `day4` decimal(16, 0) NULL DEFAULT NULL,
  `day5` decimal(16, 0) NULL DEFAULT NULL,
  `day6` decimal(16, 0) NULL DEFAULT NULL,
  `day7` decimal(16, 0) NULL DEFAULT NULL,
  `day8` decimal(16, 0) NULL DEFAULT NULL,
  `day9` decimal(16, 0) NULL DEFAULT NULL,
  `day10` decimal(16, 0) NULL DEFAULT NULL,
  `day11` decimal(16, 0) NULL DEFAULT NULL,
  `day12` decimal(16, 0) NULL DEFAULT NULL,
  `day13` decimal(16, 0) NULL DEFAULT NULL,
  `day14` decimal(16, 0) NULL DEFAULT NULL,
  `day15` decimal(16, 0) NULL DEFAULT NULL,
  `day16` decimal(16, 0) NULL DEFAULT NULL,
  `day17` decimal(16, 0) NULL DEFAULT NULL,
  `day18` decimal(16, 0) NULL DEFAULT NULL,
  `day19` decimal(16, 0) NULL DEFAULT NULL,
  `day20` decimal(16, 0) NULL DEFAULT NULL,
  `day21` decimal(16, 0) NULL DEFAULT NULL,
  `day22` decimal(16, 0) NULL DEFAULT NULL,
  `day23` decimal(16, 0) NULL DEFAULT NULL,
  `day24` decimal(16, 0) NULL DEFAULT NULL,
  `day25` decimal(16, 0) NULL DEFAULT NULL,
  `day26` decimal(16, 0) NULL DEFAULT NULL,
  `day27` decimal(16, 0) NULL DEFAULT NULL,
  `day28` decimal(16, 0) NULL DEFAULT NULL,
  `day29` decimal(16, 0) NULL DEFAULT NULL,
  `day30` decimal(16, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_kpi_user_ltv_unionkey`(`date`, `server_id`, `platform`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

2.主数据库创建存储过程，脚本如下：
CREATE PROCEDURE `create_temp_kpi_userltv_table`()
BEGIN
	DROP TEMPORARY TABLE IF EXISTS t_today_kpi_user_ltv; #由于连接池的存在，需要在创建之前删除之前创建的临时表
	CREATE TEMPORARY TABLE `t_today_kpi_user_ltv` (
		`date` date NULL DEFAULT NULL,
		`server_id` int(11) NULL DEFAULT NULL,
		`platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
		`new_user_number` int(11) NULL DEFAULT NULL,
		`total_money` decimal(16, 0) NULL DEFAULT NULL,
		`day1` decimal(16, 0) NULL DEFAULT NULL,
		`day2` decimal(16, 0) NULL DEFAULT NULL,
		`day3` decimal(16, 0) NULL DEFAULT NULL,
		`day4` decimal(16, 0) NULL DEFAULT NULL,
		`day5` decimal(16, 0) NULL DEFAULT NULL,
		`day6` decimal(16, 0) NULL DEFAULT NULL,
		`day7` decimal(16, 0) NULL DEFAULT NULL,
		`day8` decimal(16, 0) NULL DEFAULT NULL,
		`day9` decimal(16, 0) NULL DEFAULT NULL,
		`day10` decimal(16, 0) NULL DEFAULT NULL,
		`day11` decimal(16, 0) NULL DEFAULT NULL,
		`day12` decimal(16, 0) NULL DEFAULT NULL,
		`day13` decimal(16, 0) NULL DEFAULT NULL,
		`day14` decimal(16, 0) NULL DEFAULT NULL,
		`day15` decimal(16, 0) NULL DEFAULT NULL,
		`day16` decimal(16, 0) NULL DEFAULT NULL,
		`day17` decimal(16, 0) NULL DEFAULT NULL,
		`day18` decimal(16, 0) NULL DEFAULT NULL,
		`day19` decimal(16, 0) NULL DEFAULT NULL,
		`day20` decimal(16, 0) NULL DEFAULT NULL,
		`day21` decimal(16, 0) NULL DEFAULT NULL,
		`day22` decimal(16, 0) NULL DEFAULT NULL,
		`day23` decimal(16, 0) NULL DEFAULT NULL,
		`day24` decimal(16, 0) NULL DEFAULT NULL,
		`day25` decimal(16, 0) NULL DEFAULT NULL,
		`day26` decimal(16, 0) NULL DEFAULT NULL,
		`day27` decimal(16, 0) NULL DEFAULT NULL,
		`day28` decimal(16, 0) NULL DEFAULT NULL,
		`day29` decimal(16, 0) NULL DEFAULT NULL,
		`day30` decimal(16, 0) NULL DEFAULT NULL,
		UNIQUE INDEX `uk_t_kpi_user_ltv_unionkey`(`date`, `server_id`, `platform`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
END

3.日志数据库创建存储过程，脚本如下：
CREATE PROCEDURE `proc_get_user_ltv`(IN `startDate` date,IN `endDate` date,IN `usd` decimal,IN `twd` decimal,IN `hkd` decimal)
BEGIN
IF(startDate IS NULL) THEN SET startDate = 0; END IF; #如果没给开始日期，则设置为0
IF(endDate IS NULL) THEN SET endDate = DATE_ADD(DATE(now()),INTERVAL 1 DAY); END IF;#如果没有给结束日期，则设置为明天凌晨0点

SELECT 
	t1.platform,t1.date,t1.new_user_number,t2.total_money,t2.day1,t2.day2,t2.day3,t2.day4,
	t2.day5,t2.day6,t2.day7,t2.day8,t2.day9,t2.day10,t2.day11,t2.day12,t2.day13,
	t2.day14,t2.day15,t2.day16,t2.day17,t2.day18,t2.day19,t2.day20,t2.day21,t2.day22,t2.day23,t2.day24,t2.day25,t2.day26,t2.day27,t2.day28,t2.day29,t2.day30
FROM (
	SELECT 
		t2.platform,
		t1.date,
		count(t1.pid) new_user_number #导入用户数量
	FROM (SELECT pid,date(MIN(date)) date FROM t_loginlog WHERE date >= startDate AND date < endDate GROUP BY pid) t1
	INNER JOIN t_createplayerlog t2 ON t1.pid = t2.pid AND t1.date = date(t2.date)
	GROUP BY t2.platform,t1.date
) t1
LEFT JOIN (
	SELECT 
		platform,
		first_date,
		sum(if(offset_day <= 0,money,0)) day1,
		sum(if(offset_day <= 1,money,0)) day2,
		sum(if(offset_day <= 2,money,0)) day3,
		sum(if(offset_day <= 3,money,0)) day4,
		sum(if(offset_day <= 4,money,0)) day5,
		sum(if(offset_day <= 5,money,0)) day6,
		sum(if(offset_day <= 6,money,0)) day7,
		sum(if(offset_day <= 7,money,0)) day8,
		sum(if(offset_day <= 8,money,0)) day9,
		sum(if(offset_day <= 9,money,0)) day10,
		sum(if(offset_day <= 10,money,0)) day11,
		sum(if(offset_day <= 11,money,0)) day12,
		sum(if(offset_day <= 12,money,0)) day13,
		sum(if(offset_day <= 13,money,0)) day14,
		sum(if(offset_day <= 14,money,0)) day15,
		sum(if(offset_day <= 15,money,0)) day16,
		sum(if(offset_day <= 16,money,0)) day17,
		sum(if(offset_day <= 17,money,0)) day18,
		sum(if(offset_day <= 18,money,0)) day19,
		sum(if(offset_day <= 19,money,0)) day20,
		sum(if(offset_day <= 20,money,0)) day21,
		sum(if(offset_day <= 21,money,0)) day22,
		sum(if(offset_day <= 22,money,0)) day23,
		sum(if(offset_day <= 23,money,0)) day24,
		sum(if(offset_day <= 24,money,0)) day25,
		sum(if(offset_day <= 25,money,0)) day26,
		sum(if(offset_day <= 26,money,0)) day27,
		sum(if(offset_day <= 27,money,0)) day28,
		sum(if(offset_day <= 28,money,0)) day29,
		sum(if(offset_day <= 29,money,0)) day30,
		sum(money) total_money
	FROM (
		SELECT 
			t1.platform,
			t2.date first_date,
			DATEDIFF(t1.date,t2.date) offset_day,
			t1.rmb * case t1.lv when 0 then 1 when 1 then usd when 2 then twd when 3 then hkd end money
		FROM t_rechargelog t1
		LEFT JOIN (SELECT pid,date(MIN(date)) date FROM t_loginlog GROUP BY pid) t2 ON t1.pid = t2.pid
		WHERE t1.date >= startDate AND t1.date < endDate
	) t
	GROUP BY platform,first_date
) t2 ON t1.platform = t2.platform AND t1.date = t2.first_date;

END