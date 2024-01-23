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
                                   sys_code VARCHAR(255) NOT NULL,
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
COMMENT ON COLUMN efficient_sys_menu.sys_code IS '系统标识';
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
                                    sys_code VARCHAR(255) NOT NULL,
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
COMMENT ON COLUMN efficient_sys_role.sys_code IS '系统标识';
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


DROP TABLE IF EXISTS "efficient_sys_system";
CREATE TABLE efficient_sys_system (
                                         id VARCHAR(64) PRIMARY KEY,
                                         sys_code VARCHAR(255) NOT NULL,
                                         name VARCHAR(255) NOT NULL,
                                         sort int8,
                                         enabled int2 NOT NULL DEFAULT 1
);

COMMENT ON TABLE efficient_sys_system IS '系统明细';

COMMENT ON COLUMN efficient_sys_system.id IS '主键';
COMMENT ON COLUMN efficient_sys_system.sys_code IS '系统标识';
COMMENT ON COLUMN efficient_sys_system.name IS '系统名称';
COMMENT ON COLUMN efficient_sys_system.sort IS '排序';
COMMENT ON COLUMN efficient_sys_system.enabled IS '是否启用，1-是，0-否';