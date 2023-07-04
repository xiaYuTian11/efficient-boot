# efficient
基于springboot的二次封装框架

## 安装依赖
```text
mvn -Dmaven.test.skip=true install
mvn install:install-file    -DgroupId=com.aspose -DartifactId=aspose-cells -Dversion=21.11 -Dpackaging=jar   -Dfile=./aspose-cells-21.11.jar
mvn install:install-file    -DgroupId=com.aspose -DartifactId=aspose-words -Dversion=21.6 -Dpackaging=jar   -Dfile=./aspose-words-21.11.0-jdk17.jar
```
## 指定maven仓库
```xml

<repositories>
    <repository>
        <id>maven-public</id>
        <name>maven-public</name>
        <url>https://repo.maven.apache.org/maven2/</url>
        <layout>default</layout>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </repository>
</repositories>
```

## 修改版本
```text
mvn versions:set -DnewVersion=
```

## 发布
```text
mvn clean deploy -P oss-release -Dmaven.test.skip=true 
```
## 权限认证模块

## 缓存模块
ehcache.xml,目前必备的几个缓存配置
```xml

<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd" updateCheck="false">

    <diskStore path="java.io.tmpdir"/>

    <!--
    Mandatory Default Cache configuration. These settings will be applied to caches
    created programmtically using CacheManager.add(String cacheName)
    -->
    <!--
       name:缓存名称。
       maxElementsInMemory：缓存最大个数。
       eternal:对象是否永久有效，一但设置了，timeout将不起作用。
       timeToIdleSeconds：设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
       timeToLiveSeconds：设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
       overflowToDisk：当内存中对象数量达到maxElementsInMemory时，Ehcache将会对象写到磁盘中。
       diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
       maxElementsOnDisk：硬盘最大缓存个数。
       diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
       diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
       memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
       clearOnFlush：内存数量最大时是否清除。
    -->
    <cache
            name="system"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="86400"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
    <cache
            name="idempotence_cache_"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="86400"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
    <cache
            name="auth-captcha-cache"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="60"
            timeToLiveSeconds="60"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
    <cache
            name="auth-cache"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="3600"
            timeToLiveSeconds="86400"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
</ehcache>
```

## 通用模块

## 配置模块

## 文件上传下载模块
```sql
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
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `biz_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务主键',
  `store_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储类型',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_content` longblob NULL COMMENT '文件内容',
  `file_size` bigint(0) NULL DEFAULT NULL COMMENT '文件大写，kb单位',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```


## 幂等性模块

## 日志模块
```sql
/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 05/09/2022 16:32:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `log_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP',
  `log_time` timestamp NULL DEFAULT NULL COMMENT '记录日志时间',
  `request_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求路径',
  `log_opt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `log_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作内容',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '参数',
  `result_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回值',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_log_log_time_index`(`log_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

```


## swagger模块

## 定时任务模块
```sql
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

 Date: 28/08/2022 22:18:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task`  (
                             `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                             `task_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务code',
                             `task_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务描述',
                             `task_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务全限定名称',
                             `enabled` int(0) NULL DEFAULT NULL COMMENT '是否启用',
                             `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表达式',
                             `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `task_status` int(0) NULL DEFAULT NULL COMMENT '当前定时任务状态',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```