DROP TABLE IF EXISTS "efficient_sys_file_info";
CREATE TABLE public.efficient_sys_file_info (
                                                id varchar(64) NOT NULL, -- 主键
                                                biz_id varchar(64) NULL, -- 业务主键
                                                store_type varchar(10) NULL, -- 存储类型
                                                file_name text NULL, -- 文件名称
                                                file_path text NULL, -- 文件类型
                                                file_content bytea NULL, -- 文件类型
                                                file_size int8 NULL, -- 文件大写，kb单位
                                                create_time timestamp(6) NULL, -- 创建时间
                                                remark text NULL, -- 备注
                                                CONSTRAINT sys_file_info_pkey PRIMARY KEY (id)
);
CREATE INDEX index_efficient_sys_file_info_biz_id ON public.efficient_sys_file_info USING hash (biz_id);
COMMENT ON TABLE public.efficient_sys_file_info IS '文件信息';

-- Column comments

COMMENT ON COLUMN public.efficient_sys_file_info.id IS '主键';
COMMENT ON COLUMN public.efficient_sys_file_info.biz_id IS '业务主键';
COMMENT ON COLUMN public.efficient_sys_file_info.store_type IS '存储类型';
COMMENT ON COLUMN public.efficient_sys_file_info.file_name IS '文件名称';
COMMENT ON COLUMN public.efficient_sys_file_info.file_path IS '文件类型';
COMMENT ON COLUMN public.efficient_sys_file_info.file_content IS '文件类型';
COMMENT ON COLUMN public.efficient_sys_file_info.file_size IS '文件大写，kb单位';
COMMENT ON COLUMN public.efficient_sys_file_info.create_time IS '创建时间';
COMMENT ON COLUMN public.efficient_sys_file_info.remark IS '备注';