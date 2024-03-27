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
