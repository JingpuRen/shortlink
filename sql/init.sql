CREATE DATABASE link;

USE link;

# 用户表建表语句
CREATE TABLE `t_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username` varchar(256) DEFAULT NULL COMMENT '用户名',
    `password` varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 短链接分组表的建表语句
CREATE TABLE `t_group` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
    `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
    `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
    `sort_order` int(3) DEFAULT NULL COMMENT '分组排序',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 短链接表的建表语句
CREATE TABLE `t_link` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `domain` varchar(128) DEFAULT NULL COMMENT '域名',
    `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
    `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
    `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
    `click_num` int(11) DEFAULT 0 COMMENT '点击量',
    `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
    `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
    `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
    `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口 1：控制台',
    `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：用户自定义',
    `valid_date` datetime DEFAULT NULL COMMENT '有效期',
    `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_full_short_uri` (`full_short_url`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 路由表
CREATE TABLE `t_link_goto`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `gid` varchar(32) DEFAULT 'default' COMMENT  '分组标识',
    `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 监控表
CREATE TABLE `link`.`t_link_access_stats`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分组标识',
    `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '完整短链接',
    `date` date NULL DEFAULT NULL COMMENT '日期',
    `pv` int NULL DEFAULT NULL COMMENT '访问量',
    `uv` int NULL DEFAULT NULL COMMENT '独立访问数',
    `uip` int NULL DEFAULT NULL COMMENT '独立IP数',
    `hour` int NULL DEFAULT NULL COMMENT '小时',
    `weekday` int NULL DEFAULT NULL COMMENT '星期',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识：0 未删除 1 已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

ALTER TABLE t_link_access_stats ADD UNIQUE INDEX idx_unique_access_stats (full_short_url, gid, weekday, hour);

# 来访地区监控表
CREATE TABLE `t_link_locale_stats` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
    `gid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
    `date` date DEFAULT NULL COMMENT '日期',
    `cnt` int DEFAULT NULL COMMENT '访问量',
    `province` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份名称',
    `city` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '市名称',
    `adcode` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市编码',
    `country` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国家标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0表示删除 1表示未删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_locale_stats` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

# 操作系统类型监控表
CREATE TABLE `t_link_os_stats`
(
    `id`             bigint   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
    `gid`            varchar(32) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '分组标识',
    `date`           date                                    DEFAULT NULL COMMENT '日期',
    `cnt`            int                                     DEFAULT NULL COMMENT '访问量',
    `os`             varchar(64) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '操作系统',
    `create_time`    datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NOT NULL COMMENT '修改时间',
    `del_flag`       tinyint(1)                              DEFAULT NULL COMMENT '删除标识 0表示删除 1表示未删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_locale_stats` (`full_short_url`, `gid`, `date`, `os`) USING BTREE
) COMMENT = '短链接监控操作系统访问状态'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    AUTO_INCREMENT = 0
    COLLATE = utf8mb4_general_ci;

# 浏览器类型监控表
CREATE TABLE `t_link_browser_stats` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
    `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
    `date` date DEFAULT NULL COMMENT '日期',
    `cnt` int(11) DEFAULT NULL COMMENT '访问量',
    `browser` varchar(64) DEFAULT NULL COMMENT '浏览器',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`browser`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

# 访问设备类型监控
CREATE TABLE `t_link_device_stats` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
    `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
    `date` date DEFAULT NULL COMMENT '日期',
    `cnt` int(11) DEFAULT NULL COMMENT '访问量',
    `device` varchar(64) DEFAULT NULL COMMENT '访问设备',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`device`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

# 短链接访问网络统计表
CREATE TABLE `t_link_network_stats` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
    `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
    `date` date DEFAULT NULL COMMENT '日期',
    `cnt` int(11) DEFAULT NULL COMMENT '访问量',
    `network` varchar(64) DEFAULT NULL COMMENT '访问网络',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`network`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

# 短链接统计高频访问ip
CREATE TABLE `t_link_access_logs` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
    `gid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
    `user` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户信息',
    `browser` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '浏览器',
    `os` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作系统',
    `ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;