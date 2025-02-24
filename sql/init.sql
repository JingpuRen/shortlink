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

# 对t_user表进行水平分表
CREATE TABLE `t_user_0` (
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
CREATE TABLE `t_user_1` (
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
CREATE TABLE `t_user_2` (
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
CREATE TABLE `t_user_3` (
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
CREATE TABLE `t_user_4` (
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
CREATE TABLE `t_user_5` (
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
CREATE TABLE `t_user_6` (
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
CREATE TABLE `t_user_7` (
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
CREATE TABLE `t_user_8` (
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
CREATE TABLE `t_user_9` (
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
CREATE TABLE `t_user_10` (
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
CREATE TABLE `t_user_11` (
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
CREATE TABLE `t_user_12` (
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
CREATE TABLE `t_user_13` (
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
CREATE TABLE `t_user_14` (
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
CREATE TABLE `t_user_15` (
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

# 短链接表的分表语句 0 ~ 15
CREATE TABLE `t_link_0` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_1` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_2` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_3` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_4` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_5` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_6` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_7` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_8` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_9` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                            `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                            `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                            `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                            `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                            `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_10` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                             `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                             `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                             `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                             `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                             `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_11` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                             `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                             `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                             `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                             `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                             `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_12` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                             `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                             `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                             `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                             `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                             `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_13` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                             `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                             `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                             `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                             `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                             `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_14` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                             `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                             `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                             `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                             `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                             `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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
CREATE TABLE `t_link_15` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `domain` varchar(128) DEFAULT NULL COMMENT '域名',
                             `short_uri` varchar(8) DEFAULT NULL COMMENT '短链接',
                             `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                             `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
                             `click_num` int(11) DEFAULT 0 COMMENT '点击量',
                             `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
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