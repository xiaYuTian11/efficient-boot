DROP TABLE IF EXISTS "efficient_sys_log";
CREATE TABLE public.efficient_sys_log (
                                          id varchar(64) NOT NULL, -- 主键
                                          "system_id" varchar(255) NULL, -- 系统
                                          "module" varchar(255) NULL, -- 模块
                                          user_id varchar(255) NULL, -- 用户ID
                                          user_name varchar(255) NULL, -- 用户名
                                          log_ip varchar(20) NULL, -- 操作IP
                                          log_time timestamp(6) NULL, -- 记录日志时间
                                          request_url text NULL, -- 请求路径
                                          log_opt varchar(10) NULL, -- 操作类型
                                          log_content text NULL, -- 操作内容
                                          params text NULL, -- 参数
                                          result_code varchar(10) NULL, -- 结果
                                          "result" text NULL, -- 返回值
                                          "exception" text NULL, -- 异常信息
                                          CONSTRAINT efficient_sys_log_pkey PRIMARY KEY (id)
);
CREATE INDEX index_efficient_sys_log_log_opt ON public.efficient_sys_log USING btree (log_opt);
CREATE INDEX index_efficient_sys_log_log_time ON public.efficient_sys_log USING btree (log_time);
COMMENT ON TABLE public.efficient_sys_log IS '日志表';

-- Column comments

COMMENT ON COLUMN public.efficient_sys_log.id IS '主键';
COMMENT ON COLUMN public.efficient_sys_log."system_id" IS '系统ID';
COMMENT ON COLUMN public.efficient_sys_log."module" IS '模块';
COMMENT ON COLUMN public.efficient_sys_log.user_id IS '用户ID';
COMMENT ON COLUMN public.efficient_sys_log.user_name IS '用户名';
COMMENT ON COLUMN public.efficient_sys_log.log_ip IS '操作IP';
COMMENT ON COLUMN public.efficient_sys_log.log_time IS '记录日志时间';
COMMENT ON COLUMN public.efficient_sys_log.request_url IS '请求路径';
COMMENT ON COLUMN public.efficient_sys_log.log_opt IS '操作类型';
COMMENT ON COLUMN public.efficient_sys_log.log_content IS '操作内容';
COMMENT ON COLUMN public.efficient_sys_log.params IS '参数';
COMMENT ON COLUMN public.efficient_sys_log.result_code IS '结果';
COMMENT ON COLUMN public.efficient_sys_log."result" IS '返回值';
COMMENT ON COLUMN public.efficient_sys_log."exception" IS '异常信息';