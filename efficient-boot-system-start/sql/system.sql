
DROP TABLE IF EXISTS "efficient_sys_notify";
CREATE TABLE efficient_sys_notify (
                                      id VARCHAR(64) PRIMARY KEY,
                                      biz_id VARCHAR(64) not null ,
                                      biz_type VARCHAR(64),
                                      batch_num VARCHAR(64) not null ,
                                      system_id VARCHAR(10),
                                      menu_id VARCHAR(64),
                                      notify_type VARCHAR(10) not null ,
                                      recipient_system_type VARCHAR(10) not null,
                                      create_user_id VARCHAR(255) not null,
                                      create_zwdd_id VARCHAR(255),
                                      create_unit_id VARCHAR(255),
                                      create_unit_name VARCHAR(255),
                                      title text not null,
                                      content text,
                                      remark text,
                                      pc_url text ,
                                      app_url text,
                                      recipient_type varchar(10) ,
                                      recipient_user_id  VARCHAR(255) not null,
                                      recipient_zwdd_id  VARCHAR(255) ,
                                      recipient_unit_id  VARCHAR(255) ,
                                      recipient_unit_name  VARCHAR(255) ,
                                      recipient_msg_id VARCHAR(64),
                                      state varchar(10),
                                      read_time timestamp,
                                      handle_time timestamp,
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
COMMENT ON COLUMN efficient_sys_notify.biz_type IS '消息类型';
COMMENT ON COLUMN efficient_sys_notify.notify_type IS '通知类型，1-普通消息，2-短信，3-邮件，4-待办，5-工作通知，6-ding消息，7-公告，9-其他';
COMMENT ON COLUMN efficient_sys_notify.recipient_system_type IS '接收系统，1-本系统，2-YKZ ，9-其他系统';
COMMENT ON COLUMN efficient_sys_notify.create_user_id IS '创建用户id';
COMMENT ON COLUMN efficient_sys_notify.create_zwdd_id IS '创建用户政务钉钉id';
COMMENT ON COLUMN efficient_sys_notify.create_unit_id IS '创建单位id';
COMMENT ON COLUMN efficient_sys_notify.create_unit_name IS '创建单位名称';
COMMENT ON COLUMN efficient_sys_notify.title IS '标题';
COMMENT ON COLUMN efficient_sys_notify.content IS '内容';
COMMENT ON COLUMN efficient_sys_notify.remark IS '备注';
COMMENT ON COLUMN efficient_sys_notify.pc_url IS 'pc路由';
COMMENT ON COLUMN efficient_sys_notify.app_url IS 'app端路由';
COMMENT ON COLUMN efficient_sys_notify.recipient_type IS '接受人类型，1-用户，2-角色，3-机构';
COMMENT ON COLUMN efficient_sys_notify.recipient_user_id IS '接收人ID';
COMMENT ON COLUMN efficient_sys_notify.recipient_zwdd_id IS '接收人政务钉钉id';
COMMENT ON COLUMN efficient_sys_notify.recipient_unit_id IS '接收单位ID';
COMMENT ON COLUMN efficient_sys_notify.recipient_unit_name IS '接收单位名称';
COMMENT ON COLUMN efficient_sys_notify.recipient_msg_id IS '接收系统消息ID';
COMMENT ON COLUMN efficient_sys_notify.state IS '消息状态，1-待发送，2-已发送，3-已查看，4-已处理';
COMMENT ON COLUMN efficient_sys_notify.read_time IS '查看时间';
COMMENT ON COLUMN efficient_sys_notify.handle_time IS '处理时间';
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

DROP TABLE IF EXISTS "efficient_dict_code";
CREATE TABLE efficient_dict_code (
                                        id int8 NOT NULL PRIMARY KEY,
                                        code_type varchar(100) NOT NULL,
                                        code varchar(100) NOT NULL,
                                        code_name varchar(100) NOT NULL,
                                        short_name varchar(100) NULL,
                                        sort int8 NOT NULL,
                                        is_enable int2 NOT NULL default 1,
                                        parent_code varchar(100) NOT NULL,
                                        code_level int4 NULL,
                                        is_leaf int2 NOT NULL,
                                        pin_yin varchar(200) NULL,
                                        remark text NULL
);

COMMENT ON TABLE efficient_dict_code IS '字典表';

COMMENT ON COLUMN efficient_dict_code.id IS '主键';
COMMENT ON COLUMN efficient_dict_code.code_type IS '类型';
COMMENT ON COLUMN efficient_dict_code.code IS 'code';
COMMENT ON COLUMN efficient_dict_code.code_name IS '名称';
COMMENT ON COLUMN efficient_dict_code.short_name IS '简称';
COMMENT ON COLUMN efficient_dict_code.sort IS '排序';
COMMENT ON COLUMN efficient_dict_code.is_enable IS '是否启用';
COMMENT ON COLUMN efficient_dict_code.parent_code IS '父级code';
COMMENT ON COLUMN efficient_dict_code.code_level IS '层级';
COMMENT ON COLUMN efficient_dict_code.is_leaf IS '是否叶子节点';
COMMENT ON COLUMN efficient_dict_code.pin_yin IS '拼音';
COMMENT ON COLUMN efficient_dict_code.remark IS '备注';

INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(3, 'GB2261', '1', '男', NULL, 1, 1, '-1', 1, 1, 'N', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(4, 'GB2261', '2', '女', NULL, 2, 1, '-1', 1, 1, 'N', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(5, 'GB2261', '9', '未知', NULL, 9, 1, '-1', 1, 1, 'WZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(6, 'GB3304', '01', '汉族', NULL, 1, 1, '-1', 1, 1, 'HZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(7, 'GB3304', '02', '蒙古族', NULL, 2, 1, '-1', 1, 1, 'MGZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(8, 'GB3304', '03', '回族', NULL, 3, 1, '-1', 1, 1, 'HZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(9, 'GB3304', '04', '藏族', NULL, 4, 1, '-1', 1, 1, 'CZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(10, 'GB3304', '05', '维吾尔族', NULL, 5, 1, '-1', 1, 1, 'WWEZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(11, 'GB3304', '06', '苗族', NULL, 6, 1, '-1', 1, 1, 'MZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(12, 'GB3304', '07', '彝族', NULL, 7, 1, '-1', 1, 1, 'YZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(13, 'GB3304', '08', '壮族', NULL, 8, 1, '-1', 1, 1, 'ZZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(14, 'GB3304', '09', '布依族', NULL, 9, 1, '-1', 1, 1, 'BYZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(15, 'GB3304', '10', '朝鲜族', NULL, 10, 1, '-1', 1, 1, 'CXZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(16, 'GB3304', '11', '满族', NULL, 11, 1, '-1', 1, 1, 'MZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(17, 'GB3304', '12', '侗族', NULL, 12, 1, '-1', 1, 1, 'DZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(18, 'GB3304', '13', '瑶族', NULL, 13, 1, '-1', 1, 1, 'YZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(19, 'GB3304', '14', '白族', NULL, 14, 1, '-1', 1, 1, 'BZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(20, 'GB3304', '15', '土家族', NULL, 15, 1, '-1', 1, 1, 'TJZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(21, 'GB3304', '16', '哈尼族', NULL, 16, 1, '-1', 1, 1, 'HNZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(22, 'GB3304', '17', '哈萨克族', NULL, 17, 1, '-1', 1, 1, 'HSKZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(23, 'GB3304', '18', '傣族', NULL, 18, 1, '-1', 1, 1, 'DZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(24, 'GB3304', '19', '黎族', NULL, 19, 1, '-1', 1, 1, 'LZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(25, 'GB3304', '20', '傈僳族', NULL, 20, 1, '-1', 1, 1, 'LSZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(26, 'GB3304', '21', '佤族', NULL, 21, 1, '-1', 1, 1, 'WZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(27, 'GB3304', '22', '畲族', NULL, 22, 1, '-1', 1, 1, 'SZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(28, 'GB3304', '23', '高山族', NULL, 23, 1, '-1', 1, 1, 'GSZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(29, 'GB3304', '24', '拉祜族', NULL, 24, 1, '-1', 1, 1, 'LHZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(30, 'GB3304', '25', '水族', NULL, 25, 1, '-1', 1, 1, 'SZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(31, 'GB3304', '26', '东乡族', NULL, 26, 1, '-1', 1, 1, 'DXZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(32, 'GB3304', '27', '纳西族', NULL, 27, 1, '-1', 1, 1, 'NXZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(33, 'GB3304', '28', '景颇族', NULL, 28, 1, '-1', 1, 1, 'JPZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(34, 'GB3304', '29', '柯尔克孜族', NULL, 29, 1, '-1', 1, 1, 'KEKZZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(35, 'GB3304', '30', '土族', NULL, 30, 1, '-1', 1, 1, 'TZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(36, 'GB3304', '31', '达斡尔族', NULL, 31, 1, '-1', 1, 1, 'DWEZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(37, 'GB3304', '32', '仫佬族', NULL, 32, 1, '-1', 1, 1, 'MLZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(38, 'GB3304', '33', '羌族', NULL, 33, 1, '-1', 1, 1, 'QZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(39, 'GB3304', '34', '布朗族', NULL, 34, 1, '-1', 1, 1, 'BLZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(40, 'GB3304', '35', '撒拉族', NULL, 35, 1, '-1', 1, 1, 'SLZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(41, 'GB3304', '36', '毛南族', NULL, 36, 1, '-1', 1, 1, 'MNZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(42, 'GB3304', '37', '仡佬族', NULL, 37, 1, '-1', 1, 1, 'GLZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(43, 'GB3304', '38', '锡伯族', NULL, 38, 1, '-1', 1, 1, 'XBZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(44, 'GB3304', '39', '阿昌族', NULL, 39, 1, '-1', 1, 1, 'ACZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(45, 'GB3304', '40', '普米族', NULL, 40, 1, '-1', 1, 1, 'PMZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(46, 'GB3304', '41', '塔吉克族', NULL, 41, 1, '-1', 1, 1, 'TJKZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(47, 'GB3304', '42', '怒族', NULL, 42, 1, '-1', 1, 1, 'NZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(48, 'GB3304', '43', '乌孜别克族', NULL, 43, 1, '-1', 1, 1, 'WZBKZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(49, 'GB3304', '44', '俄罗斯族', NULL, 44, 1, '-1', 1, 1, 'ELSZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(50, 'GB3304', '45', '鄂温克族', NULL, 45, 1, '-1', 1, 1, 'EWKZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(51, 'GB3304', '46', '德昂族', NULL, 46, 1, '-1', 1, 1, 'DAZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(52, 'GB3304', '47', '保安族', NULL, 47, 1, '-1', 1, 1, 'BAZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(53, 'GB3304', '48', '裕固族', NULL, 48, 1, '-1', 1, 1, 'YGZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(54, 'GB3304', '49', '京族', NULL, 49, 1, '-1', 1, 1, 'JZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(55, 'GB3304', '50', '塔塔尔族', NULL, 50, 1, '-1', 1, 1, 'TTEZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(56, 'GB3304', '51', '独龙族', NULL, 51, 1, '-1', 1, 1, 'DLZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(57, 'GB3304', '52', '鄂伦春族', NULL, 52, 1, '-1', 1, 1, 'ELCZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(58, 'GB3304', '53', '赫哲族', NULL, 53, 1, '-1', 1, 1, 'HZZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(59, 'GB3304', '54', '门巴族', NULL, 54, 1, '-1', 1, 1, 'MBZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(60, 'GB3304', '55', '珞巴族', NULL, 55, 1, '-1', 1, 1, 'LBZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(61, 'GB3304', '56', '基诺族', NULL, 56, 1, '-1', 1, 1, 'JNZ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(62, 'GB3304', '91', '其他', NULL, 91, 1, '-1', 1, 1, 'QT', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(63, 'GB4762', '01', '中共党员', NULL, 1, 1, '-1', 1, 1, 'ZGDY', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(64, 'GB4762', '02', '预备党员', NULL, 2, 1, '-1', 1, 1, 'YBDY', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(65, 'GB4762', '03', '共青团员', NULL, 3, 1, '-1', 1, 1, 'GQTY', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(66, 'GB4762', '04', '民革', NULL, 4, 1, '-1', 1, 1, 'MG', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(67, 'GB4762', '05', '民盟', NULL, 5, 1, '-1', 1, 1, 'MM', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(68, 'GB4762', '06', '民建', NULL, 6, 1, '-1', 1, 1, 'MJ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(69, 'GB4762', '07', '民进', NULL, 7, 1, '-1', 1, 1, 'MJ', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(70, 'GB4762', '08', '农工党', NULL, 8, 1, '-1', 1, 1, 'NGD', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(71, 'GB4762', '09', '致公党', NULL, 9, 1, '-1', 1, 1, 'ZGD', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(72, 'GB4762', '10', '九三学社', NULL, 10, 1, '-1', 1, 1, 'JSXS', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(73, 'GB4762', '11', '台盟', NULL, 11, 1, '-1', 1, 1, 'TM', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(74, 'GB4762', '12', '无党派', NULL, 12, 1, '-1', 1, 1, 'WDP', NULL);
INSERT INTO public.efficient_dict_code
(id, code_type, code, code_name, short_name, sort, is_enable, parent_code, code_level, is_leaf, pin_yin, remark)
VALUES(75, 'GB4762', '13', '群众', NULL, 13, 1, '-1', 1, 1, 'QZ', NULL);
DROP sequence IF EXISTS efficient_dict_code_sequence;
CREATE SEQUENCE efficient_dict_code_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE efficient_dict_code ALTER COLUMN id SET DEFAULT nextval('efficient_dict_code_sequence');

ALTER SEQUENCE efficient_dict_code RESTART WITH 1000;



DROP TABLE IF EXISTS "efficient_sys_application";
CREATE TABLE efficient_sys_application (
                                           id VARCHAR(64) PRIMARY KEY,
                                           app_code VARCHAR(255) NOT NULL UNIQUE,
                                           app_name VARCHAR(255) NOT NULL,
                                           app_key text NOT NULL,
                                           app_secret text NOT NULL,
                                           pc_url text ,
                                           mobile_url text,
                                           sort int4 default 1,
                                           is_enabled int2 NOT NULL DEFAULT 1,
                                           create_time timestamp,
                                           create_user varchar(255),
                                           update_time timestamp,
                                           update_user varchar(255),
                                           is_delete int2 default 0
);

COMMENT ON TABLE efficient_sys_application IS '系统第三方应用';

COMMENT ON COLUMN efficient_sys_application.id IS '主键';
COMMENT ON COLUMN efficient_sys_application.app_code IS '系统code';
COMMENT ON COLUMN efficient_sys_application.app_name IS '系统名称';
COMMENT ON COLUMN efficient_sys_application.app_key IS '密钥对';
COMMENT ON COLUMN efficient_sys_application.app_secret IS '密钥对';
COMMENT ON COLUMN efficient_sys_application.pc_url IS 'pc端路由';
COMMENT ON COLUMN efficient_sys_application.mobile_url IS '移动端路由';
COMMENT ON COLUMN efficient_sys_application.sort IS '排序号';
COMMENT ON COLUMN efficient_sys_application.is_enabled IS '是否启用，1-是，0-否';
