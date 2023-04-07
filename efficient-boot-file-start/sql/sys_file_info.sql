/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 28/08/2022 17:47:57
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info`
(
    `id`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
    `biz_id`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务主键',
    `store_type`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '存储类型',
    `file_name`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '文件名称',
    `file_path`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '文件类型',
    `file_content` longblob COMMENT '文件类型',
    `file_size`    bigint                                                       DEFAULT NULL COMMENT '文件大写，kb单位',
    `create_time`  timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `remark`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='文件信息';

SET
FOREIGN_KEY_CHECKS = 1;
