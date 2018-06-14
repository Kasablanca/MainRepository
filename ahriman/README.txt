1.主数据库创建表t_daily_revenue，脚本如下：
CREATE TABLE `loginserver1001`.`t_daily_revenue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `server_id` int(11) NULL DEFAULT NULL,
  `server_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` decimal(15, 0) NULL DEFAULT NULL,
  `money_type` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_t_daily_revenue_date`(`date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

2.主数据库创建存储过程，脚本如下：
CREATE PROCEDURE `create_temp_revenue_table`()
BEGIN
	DROP TEMPORARY TABLE IF EXISTS t_today_revenue; #由于连接池的存在，需要在创建之前删除之前创建的临时表
	CREATE TEMPORARY TABLE `t_today_revenue` (
		  `id` int(11) NOT NULL AUTO_INCREMENT,
		  `date` date NOT NULL,
		  `server_id` int(11) NULL DEFAULT NULL,
		  `server_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
		  `platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
		  `money` decimal(15, 0) NULL DEFAULT NULL,
		  `money_type` int(11) NULL DEFAULT NULL,
		  PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
END

3.日志数据库在表t_rechargelog上添加索引，脚本如下：
ALTER TABLE t_rechargelog ADD INDEX idx_t_rechargelog_date(`date`) USING BTREE;

==========================================================================================================================

1.主数据库创建表t_daily_activeuser，脚本如下：
CREATE TABLE `loginserver1001`.`t_daily_activeuser`  (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `server_id` int(11) NOT NULL,
  `server_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `count` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

2.主数据库创建存储过程，脚本如下：
CREATE PROCEDURE `create_temp_activeuser_table`()
BEGIN
	DROP TEMPORARY TABLE IF EXISTS t_today_activeuser; #由于连接池的存在，需要在创建之前删除之前创建的临时表
	CREATE TEMPORARY TABLE `t_today_activeuser` (
		  `id` bigint(30) NOT NULL AUTO_INCREMENT,
			`date` date NOT NULL,
			`server_id` int(11) NOT NULL,
			`server_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
			`platform` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
			`count` bigint(20) NOT NULL,
			PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
END

3.日志数据库在表t_loginlog上添加索引，脚本如下：
ALTER TABLE t_loginlog ADD INDEX idx_t_loginlog_date(`date`) USING BTREE;
4.日志数据库在表t_createplayerlog上添加索引，脚本如下：
ALTER TABLE t_createplayerlog ADD UNIQUE INDEX idx_t_createplayerlog_pid(`pid`) USING BTREE;

==========================================================================================================================

1.主数据库创建表t_daily_new_role，脚本如下：
CREATE TABLE `loginserver1001`.`t_daily_new_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NULL DEFAULT NULL,
  `server_id` int(11) NULL DEFAULT NULL,
  `server_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `platform` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

2.主数据库创建存储过程，脚本如下：
CREATE PROCEDURE `create_temp_new_role_table`()
BEGIN
CREATE TEMPORARY TABLE `loginserver1001`.`t_today_new_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NULL DEFAULT NULL,
  `server_id` int(11) NULL DEFAULT NULL,
	`server_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `platform` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
END
