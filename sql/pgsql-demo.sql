/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : PostgreSQL
 Source Server Version : 110019
 Source Host           : localhost:5432
 Source Catalog        : demo
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 110019
 File Encoding         : 65001

 Date: 18/05/2023 17:19:25
*/


-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_file_info";
CREATE TABLE "public"."sys_file_info" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "biz_id" varchar(64) COLLATE "pg_catalog"."default",
  "store_type" varchar(10) COLLATE "pg_catalog"."default",
  "file_name" text COLLATE "pg_catalog"."default",
  "file_path" text COLLATE "pg_catalog"."default",
  "file_content" bytea,
  "file_size" int8,
  "create_time" timestamp(6),
  "remark" text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_file_info"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_file_info"."biz_id" IS '业务主键';
COMMENT ON COLUMN "public"."sys_file_info"."store_type" IS '存储类型';
COMMENT ON COLUMN "public"."sys_file_info"."file_name" IS '文件名称';
COMMENT ON COLUMN "public"."sys_file_info"."file_path" IS '文件类型';
COMMENT ON COLUMN "public"."sys_file_info"."file_content" IS '文件类型';
COMMENT ON COLUMN "public"."sys_file_info"."file_size" IS '文件大写，kb单位';
COMMENT ON COLUMN "public"."sys_file_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_file_info"."remark" IS '备注';
COMMENT ON TABLE "public"."sys_file_info" IS '文件信息';

-- ----------------------------
-- Records of sys_file_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_log";
CREATE TABLE "public"."sys_log" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "module" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default",
  "user_name" varchar(255) COLLATE "pg_catalog"."default",
  "log_ip" varchar(20) COLLATE "pg_catalog"."default",
  "log_time" timestamp(6),
  "request_url" text COLLATE "pg_catalog"."default",
  "log_opt" varchar(10) COLLATE "pg_catalog"."default",
  "log_content" text COLLATE "pg_catalog"."default",
  "params" text COLLATE "pg_catalog"."default",
  "result_code" varchar(10) COLLATE "pg_catalog"."default",
  "result" text COLLATE "pg_catalog"."default",
  "exception" text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_log"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_log"."module" IS '模块';
COMMENT ON COLUMN "public"."sys_log"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."sys_log"."user_name" IS '用户名';
COMMENT ON COLUMN "public"."sys_log"."log_ip" IS '操作IP';
COMMENT ON COLUMN "public"."sys_log"."log_time" IS '记录日志时间';
COMMENT ON COLUMN "public"."sys_log"."request_url" IS '请求路径';
COMMENT ON COLUMN "public"."sys_log"."log_opt" IS '操作类型';
COMMENT ON COLUMN "public"."sys_log"."log_content" IS '操作内容';
COMMENT ON COLUMN "public"."sys_log"."params" IS '参数';
COMMENT ON COLUMN "public"."sys_log"."result_code" IS '结果';
COMMENT ON COLUMN "public"."sys_log"."result" IS '返回值';
COMMENT ON COLUMN "public"."sys_log"."exception" IS '异常信息';
COMMENT ON TABLE "public"."sys_log" IS '日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_task";
CREATE TABLE "public"."sys_task" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "task_code" varchar(255) COLLATE "pg_catalog"."default",
  "task_describe" varchar(255) COLLATE "pg_catalog"."default",
  "task_class" varchar(255) COLLATE "pg_catalog"."default",
  "enabled" int4,
  "cron_expression" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "task_status" int4
)
;
COMMENT ON COLUMN "public"."sys_task"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_task"."task_code" IS '定时任务code';
COMMENT ON COLUMN "public"."sys_task"."task_describe" IS '定时任务描述';
COMMENT ON COLUMN "public"."sys_task"."task_class" IS '定时任务全限定名称';
COMMENT ON COLUMN "public"."sys_task"."enabled" IS '是否启用';
COMMENT ON COLUMN "public"."sys_task"."cron_expression" IS '表达式';
COMMENT ON COLUMN "public"."sys_task"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_task"."task_status" IS '当前定时任务状态';
COMMENT ON TABLE "public"."sys_task" IS '定时任务表';

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO "public"."sys_task" VALUES ('1', 'test', '测试定时任务', 'com.zenith.xxx.service.task.TaskTest', 1, '1 * * * * ?', '2022-08-29 17:04:10', 1);

-- ----------------------------
-- Primary Key structure for table sys_file_info
-- ----------------------------
ALTER TABLE "public"."sys_file_info" ADD CONSTRAINT "sys_file_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_log
-- ----------------------------
CREATE INDEX "sys_log_log_time_index" ON "public"."sys_log" USING btree (
  "log_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_log
-- ----------------------------
ALTER TABLE "public"."sys_log" ADD CONSTRAINT "sys_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_task
-- ----------------------------
ALTER TABLE "public"."sys_task" ADD CONSTRAINT "sys_task_pkey" PRIMARY KEY ("id");
