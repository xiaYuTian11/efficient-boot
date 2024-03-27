
DROP TABLE IF EXISTS "efficient_sys_notify";
CREATE TABLE efficient_sys_notify (
                                      id VARCHAR(64) PRIMARY KEY,
                                      biz_id VARCHAR(64) not null ,
                                      batch_num VARCHAR(64) not null ,
                                      system_id VARCHAR(10),
                                      menu_id VARCHAR(64),
                                      notify_type VARCHAR(10) not null ,
                                      recipient_system_type VARCHAR(10) not null,
                                      create_user_id VARCHAR(255) not null,
                                      title text not null,
                                      content text,
                                      remark text,
                                      pc_url text ,
                                      app_url text,
                                      recipient_type varchar(10) not null,
                                      recipient_id text,
                                      recipient_msg_id VARCHAR(64),
                                      state varchar(10),
                                      create_time timestamp,
                                      create_user varchar(64),
                                      update_time timestamp,
                                      update_user varchar(64),
                                      is_delete int2
);

COMMENT ON TABLE efficient_sys_notify IS '系统消息通知';

COMMENT ON COLUMN efficient_sys_notify.id IS '系统消息通知 ID';
COMMENT ON COLUMN efficient_sys_notify.biz_id IS '业务主键';
COMMENT ON COLUMN efficient_sys_notify.batch_num IS '批次号';
COMMENT ON COLUMN efficient_sys_notify.system_id IS '系统标识';
COMMENT ON COLUMN efficient_sys_notify.menu_id IS '菜单ID';
COMMENT ON COLUMN efficient_sys_notify.notify_type IS '通知类型，1-普通消息，2-短信，3-邮件，4-待办，5-工作通知，6-ding消息，7-公告，9-其他';
COMMENT ON COLUMN efficient_sys_notify.recipient_system_type IS '接收系统，1-本系统，2-YKZ ，9-其他系统';
COMMENT ON COLUMN efficient_sys_notify.create_user_id IS '创建用户id';
COMMENT ON COLUMN efficient_sys_notify.title IS '标题';
COMMENT ON COLUMN efficient_sys_notify.content IS '内容';
COMMENT ON COLUMN efficient_sys_notify.remark IS '备注';
COMMENT ON COLUMN efficient_sys_notify.pc_url IS 'pc路由';
COMMENT ON COLUMN efficient_sys_notify.app_url IS 'app端路由';
COMMENT ON COLUMN efficient_sys_notify.recipient_type IS '接受人类型，1-用户，2-角色，3-机构';
COMMENT ON COLUMN efficient_sys_notify.recipient_id IS '接收人ID，ALL,代表全部';
COMMENT ON COLUMN efficient_sys_notify.recipient_msg_id IS '接收系统消息ID';
COMMENT ON COLUMN efficient_sys_notify.state IS '消息状态，1-待发送，2-已发送，3-已查看，4-已处理';
COMMENT ON COLUMN efficient_sys_notify.update_time IS '修改时间';
COMMENT ON COLUMN efficient_sys_notify.create_time IS '创建时间';
COMMENT ON COLUMN efficient_sys_notify.is_delete IS '是否删除，1-是，0-否';




DROP TABLE IF EXISTS "efficient_sys_unit";
CREATE TABLE efficient_sys_unit (
                                    id VARCHAR(64) PRIMARY KEY,
                                    parent_id VARCHAR(64) ,
                                    name VARCHAR(255),
                                    short_name VARCHAR(255),
                                    level_code VARCHAR(255),
                                    unit_type VARCHAR(255),
                                    sort BIGINT,

                                    address VARCHAR(255),
                                    geocode VARCHAR(255),
                                    org_code VARCHAR(255),
                                    parent_org_code VARCHAR(255),
                                    credit_code VARCHAR(255),
                                    area_level VARCHAR(255),
                                    telephone VARCHAR(255),
                                    principal VARCHAR(255),
                                    belong text,
                                    remark text,
                                    is_enable INTEGER,

                                    create_time timestamp,
                                    create_user varchar(255),
                                    update_time timestamp,
                                    update_user varchar(255),
                                    is_delete INTEGER,
                                    pull_time timestamp
);
COMMENT ON TABLE public.efficient_sys_unit IS '机构数据';
COMMENT ON COLUMN efficient_sys_unit.id IS '机构id';
COMMENT ON COLUMN efficient_sys_unit.parent_id IS '父级机构ID';
COMMENT ON COLUMN efficient_sys_unit.name IS '机构全称';
COMMENT ON COLUMN efficient_sys_unit.short_name IS '机构简称';
COMMENT ON COLUMN efficient_sys_unit.level_code IS '机构层级码';
COMMENT ON COLUMN efficient_sys_unit.unit_type IS '机构类型,1-分类，2-单位，3-内部机构';
COMMENT ON COLUMN efficient_sys_unit.sort IS '同级排序字段';
COMMENT ON COLUMN efficient_sys_unit.address IS '单位地址';
COMMENT ON COLUMN efficient_sys_unit.geocode IS '区划代码';
COMMENT ON COLUMN efficient_sys_unit.org_code IS '政务钉钉组织机构code';
COMMENT ON COLUMN efficient_sys_unit.parent_org_code IS '父组织机构code';
COMMENT ON COLUMN efficient_sys_unit.credit_code IS '统一信用代码';
COMMENT ON COLUMN efficient_sys_unit.remark IS '备注';
COMMENT ON COLUMN efficient_sys_unit.area_level IS '区域级别';
COMMENT ON COLUMN efficient_sys_unit.telephone IS '单位电话';
COMMENT ON COLUMN efficient_sys_unit.principal IS '单位负责人userCode';
COMMENT ON COLUMN efficient_sys_unit.belong IS '单位层级中文';
COMMENT ON COLUMN efficient_sys_unit.update_time IS '更新时间';
COMMENT ON COLUMN efficient_sys_unit.create_time IS '创建时间戳';
COMMENT ON COLUMN efficient_sys_unit.is_delete IS '删除标识 1 表示删除，0 表示未删除';
COMMENT ON COLUMN efficient_sys_unit.is_enable IS '是否启用 1-启用，0-停用';
COMMENT ON COLUMN efficient_sys_unit.pull_time IS '拉取时间';


DROP TABLE IF EXISTS "efficient_sys_user";
CREATE TABLE efficient_sys_user (
                                    id VARCHAR(64) PRIMARY KEY,
                                    name VARCHAR(255),
                                    account VARCHAR(255),
                                    password VARCHAR(255),
                                    zwdd_id VARCHAR(255),
                                    phone VARCHAR(255),
                                    id_card VARCHAR(255),
                                    is_enable INTEGER,

                                    expiration_time timestamp,
                                    update_password_time timestamp,
                                    last_login_time timestamp,

                                    create_time timestamp,
                                    create_user varchar(255),
                                    update_time timestamp,
                                    update_user varchar(255),
                                    is_delete INTEGER,
                                    pull_time timestamp
);

COMMENT ON TABLE efficient_sys_user IS '用户信息';

COMMENT ON COLUMN efficient_sys_user.id IS '用户中心 ID';
COMMENT ON COLUMN efficient_sys_user.name IS '姓名';
COMMENT ON COLUMN efficient_sys_user.account IS '账号';
COMMENT ON COLUMN efficient_sys_user.password IS '密码';
COMMENT ON COLUMN efficient_sys_user.zwdd_id IS '政务钉Id';
COMMENT ON COLUMN efficient_sys_user.phone IS '电话';
COMMENT ON COLUMN efficient_sys_user.id_card IS '身份证';
COMMENT ON COLUMN efficient_sys_user.is_enable IS '是否启用 1-启用，0-停用';
COMMENT ON COLUMN efficient_sys_user.expiration_time IS '过期时间';
COMMENT ON COLUMN efficient_sys_user.update_password_time IS '上次修改密码时间';
COMMENT ON COLUMN efficient_sys_user.last_login_time IS '上传登录时间';
COMMENT ON COLUMN efficient_sys_user.pull_time IS '拉取时间';

DROP TABLE IF EXISTS "efficient_sys_user_post";
CREATE TABLE efficient_sys_user_post (
                                         id VARCHAR(64) PRIMARY KEY,
                                         user_id VARCHAR(255),
                                         dept_id VARCHAR(255),
                                         dept_level_code VARCHAR(255),
                                         unit_id VARCHAR(255),
                                         unit_level_code VARCHAR(255),
                                         permission_type varchar(10),
                                         main_job int2,
                                         join_date timestamp,
                                         sort BIGINT,
                                         post_name VARCHAR(255),
                                         create_time timestamp,
                                         update_time timestamp,
                                         is_delete INTEGER,
                                         pull_time timestamp
);

COMMENT ON TABLE efficient_sys_user_post IS '用户职位信息';

COMMENT ON COLUMN efficient_sys_user_post.id IS '主键';
COMMENT ON COLUMN efficient_sys_user_post.user_id IS '用户id';
COMMENT ON COLUMN efficient_sys_user_post.main_job IS '是否主职务';
COMMENT ON COLUMN efficient_sys_user_post.dept_id IS '部门ID';
COMMENT ON COLUMN efficient_sys_user_post.dept_level_code IS '部门层级码';
COMMENT ON COLUMN efficient_sys_user_post.unit_id IS '单位ID';
COMMENT ON COLUMN efficient_sys_user_post.unit_level_code IS '单位层级码';
COMMENT ON COLUMN efficient_sys_user_post.post_name IS '职务';
COMMENT ON COLUMN efficient_sys_user_post.sort IS '排序';
COMMENT ON COLUMN efficient_sys_user_post.permission_type IS '权限类型，1-个人，2-部门，3-单位，9-自定义';
COMMENT ON COLUMN efficient_sys_user_post.pull_time IS '拉取时间';
COMMENT ON COLUMN efficient_sys_user_post.join_date IS '加入时间';



DROP TABLE IF EXISTS "efficient_sys_config";
CREATE TABLE efficient_sys_config (
                                         id VARCHAR(64) PRIMARY KEY,
                                         code VARCHAR(255),
                                         config text,
                                         remark VARCHAR(255),
                                         is_enable int2 default 0
);

COMMENT ON TABLE efficient_sys_config IS '系统配置';

COMMENT ON COLUMN efficient_sys_config.id IS '主键';
COMMENT ON COLUMN efficient_sys_config.code IS 'code';
COMMENT ON COLUMN efficient_sys_config.config IS '配置';
COMMENT ON COLUMN efficient_sys_config.remark IS '备注';
COMMENT ON COLUMN efficient_sys_config.is_enable IS '是否启用';
