-- 文件上传
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

DROP TABLE IF EXISTS "efficient_sys_log";
CREATE TABLE public.efficient_sys_log (
                                          id varchar(64) NOT NULL, -- 主键
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


--  定时任务

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

-- YKZ 相关
DROP TABLE IF EXISTS "efficient_ykz_org";
CREATE TABLE efficient_ykz_org (
                                   id VARCHAR(64) PRIMARY KEY,
                                   ykz_id BIGINT ,
                                   name VARCHAR(255),
                                   org_type VARCHAR(255),
                                   parent_id BIGINT,
                                   display_order BIGINT,
                                   is_deleted INTEGER,
                                   is_enable INTEGER,
                                   create_time BIGINT,
                                   gov_address VARCHAR(255),
                                   gov_division_code VARCHAR(255),
                                   gov_business_strip_codes VARCHAR(255),
                                   gov_institution_level_code VARCHAR(255),
                                   gov_short_name VARCHAR(255),
                                   organization_code VARCHAR(255),
                                   parent_organization_code VARCHAR(255),
                                   principal VARCHAR(255),
                                   update_time BIGINT,
                                   credit_code VARCHAR(255),
                                   remark VARCHAR(255),
                                   area_level VARCHAR(255),
                                   error_info text,
                                   pull_time timestamp
);
COMMENT ON TABLE public.efficient_ykz_org IS 'YKZ 机构数据';
COMMENT ON COLUMN efficient_ykz_org.id IS '机构id';
COMMENT ON COLUMN efficient_ykz_org.name IS '机构全称';
COMMENT ON COLUMN efficient_ykz_org.org_type IS '机构类型';
COMMENT ON COLUMN efficient_ykz_org.parent_id IS '机构父级id';
COMMENT ON COLUMN efficient_ykz_org.display_order IS '同级排序字段';
COMMENT ON COLUMN efficient_ykz_org.is_deleted IS '删除标识 1 表示删除，0 表示未删除';
COMMENT ON COLUMN efficient_ykz_org.is_enable IS '是否启用 1-启用，0-停用';
COMMENT ON COLUMN efficient_ykz_org.create_time IS '创建时间戳';
COMMENT ON COLUMN efficient_ykz_org.gov_address IS '单位地址';
COMMENT ON COLUMN efficient_ykz_org.gov_division_code IS '行政区划Code';
COMMENT ON COLUMN efficient_ykz_org.gov_business_strip_codes IS '条线Code列表';
COMMENT ON COLUMN efficient_ykz_org.gov_institution_level_code IS '机构/单位级别';
COMMENT ON COLUMN efficient_ykz_org.gov_short_name IS '机构简称';
COMMENT ON COLUMN efficient_ykz_org.organization_code IS '政务钉钉组织机构code';
COMMENT ON COLUMN efficient_ykz_org.parent_organization_code IS '父组织机构code';
COMMENT ON COLUMN efficient_ykz_org.principal IS '单位负责人userCode';
COMMENT ON COLUMN efficient_ykz_org.update_time IS '更新时间';
COMMENT ON COLUMN efficient_ykz_org.credit_code IS '统一信用代码';
COMMENT ON COLUMN efficient_ykz_org.remark IS '备注';
COMMENT ON COLUMN efficient_ykz_org.area_level IS '区域级别';
COMMENT ON COLUMN efficient_ykz_org.pull_time IS '拉取时间';
COMMENT ON COLUMN efficient_ykz_org.error_info IS '错误信息';


DROP TABLE IF EXISTS "efficient_ykz_user";
CREATE TABLE efficient_ykz_user (
                                    id VARCHAR(64) PRIMARY KEY,
                                    ykz_id BIGINT,
                                    name VARCHAR(255),
                                    username VARCHAR(255),
                                    account_id VARCHAR(255),
                                    employee_code VARCHAR(255),
                                    mobile VARCHAR(255),
                                    error_info text,
                                    pull_time timestamp
);

COMMENT ON TABLE efficient_ykz_user IS 'YKZ 用户信息';

COMMENT ON COLUMN efficient_ykz_user.id IS '用户中心 ID';
COMMENT ON COLUMN efficient_ykz_user.name IS '姓名';
COMMENT ON COLUMN efficient_ykz_user.username IS '用户名';
COMMENT ON COLUMN efficient_ykz_user.account_id IS '政务钉钉 ID';
COMMENT ON COLUMN efficient_ykz_user.employee_code IS '政务钉钉员工编号';
COMMENT ON COLUMN efficient_ykz_user.mobile IS '电话号码';
COMMENT ON COLUMN efficient_ykz_user.pull_time IS '拉取时间';
COMMENT ON COLUMN efficient_ykz_user.error_info IS '错误信息';

DROP TABLE IF EXISTS "efficient_ykz_user_post";
CREATE TABLE efficient_ykz_user_post (
                                         id VARCHAR(64) PRIMARY KEY,
                                         account_id VARCHAR(255),
                                         organization_code VARCHAR(255),
                                         post_type INTEGER,
                                         pos_job VARCHAR(255),
                                         error_info text,
                                         pull_time timestamp
);

COMMENT ON TABLE efficient_ykz_user_post IS '愉快政用户职位信息';

COMMENT ON COLUMN efficient_ykz_user.account_id IS '政务钉钉 ID';
COMMENT ON COLUMN efficient_ykz_user_post.organization_code IS '职务所在机构code';
COMMENT ON COLUMN efficient_ykz_user_post.post_type IS '任职类型 1主职、2兼职、3挂职、4借调';
COMMENT ON COLUMN efficient_ykz_user_post.pos_job IS '职务';
COMMENT ON COLUMN efficient_ykz_user_post.pull_time IS '拉取时间';
COMMENT ON COLUMN efficient_ykz_user_post.error_info IS '错误信息';




-- 用户中心


DROP TABLE IF EXISTS "efficient_sys_user";
CREATE TABLE efficient_sys_user (
                                    id VARCHAR(64) PRIMARY KEY,
                                    account VARCHAR(255) NOT NULL,
                                    password VARCHAR(255) NOT NULL,
                                    name VARCHAR(255),
                                    user_num VARCHAR(255),
                                    icon VARCHAR(100),
                                    sex VARCHAR(10),
                                    phone VARCHAR(100),
                                    enabled int2 NOT NULL DEFAULT 1,
                                    birthday date,
                                    id_card varchar(100),
                                    zwdd_id varchar(100),
                                    other_sys_id varchar(100),
                                    secret_level int4 NOT NULL DEFAULT 0,
                                    is_builtin int2 NOT NULL DEFAULT 0,
                                    is_read_only int2 NOT NULL DEFAULT 0,
                                    is_reset_password int2 NOT NULL DEFAULT 0,
                                    expiration_time timestamp,
                                    last_update_password_time timestamp,
                                    last_login_time timestamp,
                                    unlock_time timestamp,
                                    check_status int4,
                                    sort int8,
                                    create_time timestamp,
                                    create_user varchar(255),
                                    update_time timestamp,
                                    update_user varchar(255),
                                    is_delete int2 NOT NULL DEFAULT 0,
                                    remark text,
                                    extend1 text,
                                    extend2 text,
                                    extend3 text,
                                    extend4 text,
                                    extend5 text
);

COMMENT ON TABLE efficient_sys_user IS '用户表';

COMMENT ON COLUMN efficient_sys_user.id IS '主键';
COMMENT ON COLUMN efficient_sys_user.account IS '账号';
COMMENT ON COLUMN efficient_sys_user.password IS '密码';
COMMENT ON COLUMN efficient_sys_user.name IS '姓名';
COMMENT ON COLUMN efficient_sys_user.user_num IS '用户编号';
COMMENT ON COLUMN efficient_sys_user.icon IS '用户头像ID';
COMMENT ON COLUMN efficient_sys_user.sex IS '性别，1-男，2-女，3-未知';
COMMENT ON COLUMN efficient_sys_user.phone IS '电话';
COMMENT ON COLUMN efficient_sys_user.enabled IS '是否启用，1-是，0-否';
COMMENT ON COLUMN efficient_sys_user.birthday IS '出生日期';
COMMENT ON COLUMN efficient_sys_user.id_card IS '身份证';
COMMENT ON COLUMN efficient_sys_user.zwdd_id IS '政务钉钉ID';
COMMENT ON COLUMN efficient_sys_user.other_sys_id IS '第三方系统ID';
COMMENT ON COLUMN efficient_sys_user.secret_level IS '秘密层级，0-非密，2-内部，4-秘密，6-机密，8-绝密';
COMMENT ON COLUMN efficient_sys_user.is_builtin IS '是否内建用户，1-是，0-否';
COMMENT ON COLUMN efficient_sys_user.is_reset_password IS '是否重置过密码，1-是，0-否';
COMMENT ON COLUMN efficient_sys_user.expiration_time IS '过期时间';
COMMENT ON COLUMN efficient_sys_user.last_update_password_time IS '上次修改密码时间';
COMMENT ON COLUMN efficient_sys_user.last_login_time IS '上次登录时间';
COMMENT ON COLUMN efficient_sys_user.unlock_time IS '解锁时间';
COMMENT ON COLUMN efficient_sys_user.check_status IS '审查状态，1-审查中，2-审查通过，3-审查未通过';
COMMENT ON COLUMN efficient_sys_user.sort IS '排序';
COMMENT ON COLUMN efficient_sys_user.create_time IS '创建时间';
COMMENT ON COLUMN efficient_sys_user.create_user IS '创建人';
COMMENT ON COLUMN efficient_sys_user.update_time IS '修改时间';
COMMENT ON COLUMN efficient_sys_user.update_user IS '修改人';
COMMENT ON COLUMN efficient_sys_user.is_delete IS '是否删除，1-是，0-否';
COMMENT ON COLUMN efficient_sys_user.remark IS '备注';
COMMENT ON COLUMN efficient_sys_user.extend1 IS '扩展字段1';
COMMENT ON COLUMN efficient_sys_user.extend2 IS '扩展字段2';
COMMENT ON COLUMN efficient_sys_user.extend3 IS '扩展字段3';
COMMENT ON COLUMN efficient_sys_user.extend4 IS '扩展字段4';
COMMENT ON COLUMN efficient_sys_user.extend5 IS '扩展字段5';




DROP TABLE IF EXISTS "efficient_sys_org";
CREATE TABLE efficient_sys_org (
                                   id VARCHAR(64) PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   sort_name VARCHAR(255) NOT NULL,
                                   parent_id VARCHAR(255) NOT NULL,
                                   level_code VARCHAR(255) NOT NULL,
                                   code VARCHAR(255),
                                   parent_code VARCHAR(255),
                                   zwdd_id VARCHAR(255),
                                   org_type VARCHAR(100),
                                   phone VARCHAR(100),
                                   enabled int2 NOT NULL DEFAULT 1,

                                   geocode varchar(100) ,
                                   full_path text NOT NULL,
                                   secret_level int4 NOT NULL DEFAULT 0,
                                   sort int8,
                                   create_time timestamp,
                                   create_user varchar(255),
                                   update_time timestamp,
                                   update_user varchar(255),
                                   is_delete int2 NOT NULL DEFAULT 0,
                                   remark text,
                                   extend1 text,
                                   extend2 text,
                                   extend3 text,
                                   extend4 text,
                                   extend5 text
);

COMMENT ON TABLE efficient_sys_org IS '机构表';

COMMENT ON COLUMN efficient_sys_org.id IS '主键';
COMMENT ON COLUMN efficient_sys_org.name IS '机构名称';
COMMENT ON COLUMN efficient_sys_org.sort_name IS '机构简称';
COMMENT ON COLUMN efficient_sys_org.parent_id IS '父级机构ID';
COMMENT ON COLUMN efficient_sys_org.level_code IS '机构层级码';
COMMENT ON COLUMN efficient_sys_org.code IS '机构code';
COMMENT ON COLUMN efficient_sys_org.parent_code IS '父级机构code';
COMMENT ON COLUMN efficient_sys_org.zwdd_id IS '政务钉Id';
COMMENT ON COLUMN efficient_sys_org.org_type IS '机构类型';
COMMENT ON COLUMN efficient_sys_org.phone IS '电话';
COMMENT ON COLUMN efficient_sys_org.enabled IS '是否启用，1-是，0-否';
COMMENT ON COLUMN efficient_sys_org.geocode IS '区划code';
COMMENT ON COLUMN efficient_sys_org.full_path IS '机构全路径';
COMMENT ON COLUMN efficient_sys_org.secret_level IS '秘密层级，0-非密，2-内部，4-秘密，6-机密，8-绝密';

COMMENT ON COLUMN efficient_sys_org.sort IS '排序';
COMMENT ON COLUMN efficient_sys_org.create_time IS '创建时间';
COMMENT ON COLUMN efficient_sys_org.create_user IS '创建人';
COMMENT ON COLUMN efficient_sys_org.update_time IS '修改时间';
COMMENT ON COLUMN efficient_sys_org.update_user IS '修改人';
COMMENT ON COLUMN efficient_sys_org.is_delete IS '是否删除，1-是，0-否';
COMMENT ON COLUMN efficient_sys_org.remark IS '备注';
COMMENT ON COLUMN efficient_sys_org.extend1 IS '扩展字段1';
COMMENT ON COLUMN efficient_sys_org.extend2 IS '扩展字段2';
COMMENT ON COLUMN efficient_sys_org.extend3 IS '扩展字段3';
COMMENT ON COLUMN efficient_sys_org.extend4 IS '扩展字段4';
COMMENT ON COLUMN efficient_sys_org.extend5 IS '扩展字段5';


DROP TABLE IF EXISTS "efficient_sys_unit_user";
CREATE TABLE efficient_sys_unit_user (
                                         id VARCHAR(64) PRIMARY KEY,
                                         org_dept_id VARCHAR(255) NOT NULL,
                                         org_unit_id VARCHAR(255) NOT NULL,
                                         user_id VARCHAR(255) NOT NULL,
                                         is_main_post int2 NOT NULL default 1,
                                         post_info varchar(255) ,
                                         join_date date ,
                                         enabled int2 NOT NULL DEFAULT 1,
                                         leader_type varchar(100),

                                         sort int8,
                                         create_time timestamp,
                                         create_user varchar(255),
                                         update_time timestamp,
                                         update_user varchar(255),
                                         is_delete int2 NOT NULL DEFAULT 0,
                                         remark text,
                                         extend1 text,
                                         extend2 text,
                                         extend3 text,
                                         extend4 text,
                                         extend5 text
);

COMMENT ON TABLE efficient_sys_unit_user IS '单位用户表';

COMMENT ON COLUMN efficient_sys_unit_user.id IS '主键';
COMMENT ON COLUMN efficient_sys_unit_user.org_dept_id IS '部门ID';
COMMENT ON COLUMN efficient_sys_unit_user.org_unit_id IS '单位ID';
COMMENT ON COLUMN efficient_sys_unit_user.user_id IS '用户id';
COMMENT ON COLUMN efficient_sys_unit_user.is_main_post IS '是否主职务，1-是，0-否';
COMMENT ON COLUMN efficient_sys_unit_user.post_info IS '职务信息';
COMMENT ON COLUMN efficient_sys_unit_user.join_date IS '任职时间';
COMMENT ON COLUMN efficient_sys_unit_user.enabled IS '是否启用，1-是，0-否';
COMMENT ON COLUMN efficient_sys_unit_user.leader_type IS '领导类型，自定义';

COMMENT ON COLUMN efficient_sys_unit_user.sort IS '排序';
COMMENT ON COLUMN efficient_sys_unit_user.create_time IS '创建时间';
COMMENT ON COLUMN efficient_sys_unit_user.create_user IS '创建人';
COMMENT ON COLUMN efficient_sys_unit_user.update_time IS '修改时间';
COMMENT ON COLUMN efficient_sys_unit_user.update_user IS '修改人';
COMMENT ON COLUMN efficient_sys_unit_user.is_delete IS '是否删除，1-是，0-否';
COMMENT ON COLUMN efficient_sys_unit_user.remark IS '备注';
COMMENT ON COLUMN efficient_sys_unit_user.extend1 IS '扩展字段1';
COMMENT ON COLUMN efficient_sys_unit_user.extend2 IS '扩展字段2';
COMMENT ON COLUMN efficient_sys_unit_user.extend3 IS '扩展字段3';
COMMENT ON COLUMN efficient_sys_unit_user.extend4 IS '扩展字段4';
COMMENT ON COLUMN efficient_sys_unit_user.extend5 IS '扩展字段5';

DROP TABLE IF EXISTS "efficient_sys_menu";
CREATE TABLE efficient_sys_menu (
                                    id VARCHAR(64) PRIMARY KEY,
                                    system VARCHAR(255) NOT NULL,
                                    name VARCHAR(255) NOT NULL,
                                    url varchar(255),
                                    menu_type int2,
                                    menu_code varchar(255),
                                    level_code varchar(255),
                                    parent_id varchar(255),
                                    enabled int2 NOT NULL DEFAULT 1,

                                    sort int8,
                                    create_time timestamp,
                                    create_user varchar(255),
                                    update_time timestamp,
                                    update_user varchar(255),
                                    is_delete int2 NOT NULL DEFAULT 0,
                                    remark text,
                                    extend1 text,
                                    extend2 text,
                                    extend3 text,
                                    extend4 text,
                                    extend5 text
);

COMMENT ON TABLE efficient_sys_menu IS '菜单表';

COMMENT ON COLUMN efficient_sys_menu.id IS '主键';
COMMENT ON COLUMN efficient_sys_menu.system IS '系统标识';
COMMENT ON COLUMN efficient_sys_menu.name IS '菜单名称';
COMMENT ON COLUMN efficient_sys_menu.url IS '菜单路由';
COMMENT ON COLUMN efficient_sys_menu.menu_type IS '菜单类型，1-菜单，2-按钮';
COMMENT ON COLUMN efficient_sys_menu.menu_code IS '菜单code';
COMMENT ON COLUMN efficient_sys_menu.level_code IS '菜单层级';
COMMENT ON COLUMN efficient_sys_menu.parent_id IS '父级ID';
COMMENT ON COLUMN efficient_sys_menu.enabled IS '是否启用，1-是，0-否';

COMMENT ON COLUMN efficient_sys_menu.sort IS '排序';
COMMENT ON COLUMN efficient_sys_menu.create_time IS '创建时间';
COMMENT ON COLUMN efficient_sys_menu.create_user IS '创建人';
COMMENT ON COLUMN efficient_sys_menu.update_time IS '修改时间';
COMMENT ON COLUMN efficient_sys_menu.update_user IS '修改人';
COMMENT ON COLUMN efficient_sys_menu.is_delete IS '是否删除，1-是，0-否';
COMMENT ON COLUMN efficient_sys_menu.remark IS '备注';
COMMENT ON COLUMN efficient_sys_menu.extend1 IS '扩展字段1';
COMMENT ON COLUMN efficient_sys_menu.extend2 IS '扩展字段2';
COMMENT ON COLUMN efficient_sys_menu.extend3 IS '扩展字段3';
COMMENT ON COLUMN efficient_sys_menu.extend4 IS '扩展字段4';
COMMENT ON COLUMN efficient_sys_menu.extend5 IS '扩展字段5';


DROP TABLE IF EXISTS "efficient_sys_role";
CREATE TABLE efficient_sys_role (
                                    id VARCHAR(64) PRIMARY KEY,
                                    system VARCHAR(255) NOT NULL,
                                    name VARCHAR(255) NOT NULL,
                                    role_code VARCHAR(255) NOT NULL,
                                    org_id varchar(255),
                                    org_level_code varchar(255),
                                    is_accredit int2 NOT NULL DEFAULT 0,
                                    role_type int2 NOT NULL DEFAULT 0,

                                    enabled int2 NOT NULL DEFAULT 1,
                                    sort int8,
                                    create_time timestamp,
                                    create_user varchar(255),
                                    update_time timestamp,
                                    update_user varchar(255),
                                    is_delete int2 NOT NULL DEFAULT 0,
                                    remark text,
                                    extend1 text,
                                    extend2 text,
                                    extend3 text,
                                    extend4 text,
                                    extend5 text
);

COMMENT ON TABLE efficient_sys_role IS '角色表';

COMMENT ON COLUMN efficient_sys_role.id IS '主键';
COMMENT ON COLUMN efficient_sys_role.system IS '系统标识';
COMMENT ON COLUMN efficient_sys_role.name IS '菜单名称';
COMMENT ON COLUMN efficient_sys_role.role_code IS '菜单名称';
COMMENT ON COLUMN efficient_sys_role.org_id IS '机构ID';
COMMENT ON COLUMN efficient_sys_role.org_level_code IS '机构层级码';
COMMENT ON COLUMN efficient_sys_role.is_accredit IS '是否授权给下级机构使用，1-是，0-否';
COMMENT ON COLUMN efficient_sys_role.role_type IS '角色类型，0-常规角色，7-系统管理员角色，8-系统保密员角色，9-系统审计员角色';


COMMENT ON COLUMN efficient_sys_role.enabled IS '是否启用，1-是，0-否';
COMMENT ON COLUMN efficient_sys_role.sort IS '排序';
COMMENT ON COLUMN efficient_sys_role.create_time IS '创建时间';
COMMENT ON COLUMN efficient_sys_role.create_user IS '创建人';
COMMENT ON COLUMN efficient_sys_role.update_time IS '修改时间';
COMMENT ON COLUMN efficient_sys_role.update_user IS '修改人';
COMMENT ON COLUMN efficient_sys_role.is_delete IS '是否删除，1-是，0-否';
COMMENT ON COLUMN efficient_sys_role.remark IS '备注';
COMMENT ON COLUMN efficient_sys_role.extend1 IS '扩展字段1';
COMMENT ON COLUMN efficient_sys_role.extend2 IS '扩展字段2';
COMMENT ON COLUMN efficient_sys_role.extend3 IS '扩展字段3';
COMMENT ON COLUMN efficient_sys_role.extend4 IS '扩展字段4';
COMMENT ON COLUMN efficient_sys_role.extend5 IS '扩展字段5';


DROP TABLE IF EXISTS "efficient_sys_role_menu";
CREATE TABLE efficient_sys_role_menu (
                                         id VARCHAR(64) PRIMARY KEY,
                                         role_id VARCHAR(255) NOT NULL,
                                         menu_id VARCHAR(255) NOT NULL,
                                         data_auth int2,
                                         data_auth_json text
);

COMMENT ON TABLE efficient_sys_role_menu IS '角色菜单表';

COMMENT ON COLUMN efficient_sys_role_menu.id IS '主键';
COMMENT ON COLUMN efficient_sys_role_menu.role_id IS '角色id';
COMMENT ON COLUMN efficient_sys_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN efficient_sys_role_menu.data_auth IS '数据权限，1-个人权限，2-部门权限，3-单位权限，3-所有权限，4-自定义权限（不包含下级），5-自定义权限（包含下级）';
COMMENT ON COLUMN efficient_sys_role_menu.data_auth_json IS '权限数据';
