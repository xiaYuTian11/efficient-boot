DROP TABLE IF EXISTS "efficient_sys_task";
CREATE TABLE public.efficient_sys_task (
                                           id varchar(255) NOT NULL, -- 主键
                                           task_code varchar(255) NULL, -- 定时任务code
                                           task_describe varchar(255) NULL, -- 定时任务描述
                                           task_class varchar(255) NULL, -- 定时任务全限定名称
                                           enabled int4 NULL, -- 是否启用
                                           cron_expression varchar(255) NULL, -- 表达式
                                           create_time timestamp(6) NULL, -- 创建时间
                                           task_status int4 NULL, -- 当前定时任务状态
                                           CONSTRAINT sys_task_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE public.efficient_sys_task IS '定时任务表';

-- Column comments

COMMENT ON COLUMN public.efficient_sys_task.id IS '主键';
COMMENT ON COLUMN public.efficient_sys_task.task_code IS '定时任务code';
COMMENT ON COLUMN public.efficient_sys_task.task_describe IS '定时任务描述';
COMMENT ON COLUMN public.efficient_sys_task.task_class IS '定时任务全限定名称';
COMMENT ON COLUMN public.efficient_sys_task.enabled IS '是否启用';
COMMENT ON COLUMN public.efficient_sys_task.cron_expression IS '表达式';
COMMENT ON COLUMN public.efficient_sys_task.create_time IS '创建时间';
COMMENT ON COLUMN public.efficient_sys_task.task_status IS '当前定时任务状态';