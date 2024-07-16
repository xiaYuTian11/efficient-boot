DROP TABLE IF EXISTS "efficient_dynamic_forms";
CREATE TABLE efficient_dynamic_forms (
                                         id VARCHAR(64) PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
                                         description text,
                                         remark text,
                                         sort int4 default 1,
                                         is_enabled int2 NOT NULL DEFAULT 1,
                                         data_permissions varchar(10),
                                         form_style text,
                                         list_style text,
                                         create_time timestamp,
                                         create_user varchar(255),
                                         update_time timestamp,
                                         update_user varchar(255),
                                         is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms IS '系统动态表单';

COMMENT ON COLUMN efficient_dynamic_forms.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms.name IS '表单名称';
COMMENT ON COLUMN efficient_dynamic_forms.description IS '表单描述';
COMMENT ON COLUMN efficient_dynamic_forms.sort IS '排序号';
COMMENT ON COLUMN efficient_dynamic_forms.remark IS '备注';
COMMENT ON COLUMN efficient_dynamic_forms.is_enabled IS '是否启用，1-是，0-否';
COMMENT ON COLUMN efficient_dynamic_forms.data_permissions IS '数据权限，1-完全开放，所有人可看，2-只有本人能看，3-按照单位层级查询';
COMMENT ON COLUMN efficient_dynamic_forms.form_style IS '表单样式';
COMMENT ON COLUMN efficient_dynamic_forms.list_style IS '列表样式';


DROP TABLE IF EXISTS "efficient_dynamic_forms_fields";
CREATE TABLE efficient_dynamic_forms_fields  (
                                                 id VARCHAR(64) PRIMARY KEY,
                                                 forms_id VARCHAR(255) NOT NULL,
                                                 field_name VARCHAR(255) NOT NULL,
                                                 field_type VARCHAR(255) NOT NULL,
                                                 field_option_value text,
                                                 field_default_value text,
                                                 sort int4 default 1,
                                                 is_required int2 DEFAULT 0,
                                                 is_show_list int2 DEFAULT 0,
                                                 show_list_sort int4 default 1,
                                                 is_search_list int2 DEFAULT 0,
                                                 remark text,
                                                 create_time timestamp,
                                                 create_user varchar(255),
                                                 update_time timestamp,
                                                 update_user varchar(255),
                                                 is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms_fields IS '系统动态表单-字段配置';

COMMENT ON COLUMN efficient_dynamic_forms_fields.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_fields.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_fields.field_name IS '字段名称';
COMMENT ON COLUMN efficient_dynamic_forms_fields.field_type IS '字段类型';
COMMENT ON COLUMN efficient_dynamic_forms_fields.field_option_value IS '字段可选值，一般用于下拉';
COMMENT ON COLUMN efficient_dynamic_forms_fields.field_default_value IS '字段默认值';
COMMENT ON COLUMN efficient_dynamic_forms_fields.sort IS '字段排序';
COMMENT ON COLUMN efficient_dynamic_forms_fields.is_required IS '是否必填';
COMMENT ON COLUMN efficient_dynamic_forms_fields.is_show_list IS '是否在列表上展示';
COMMENT ON COLUMN efficient_dynamic_forms_fields.show_list_sort IS '展示在列表上的顺序';
COMMENT ON COLUMN efficient_dynamic_forms_fields.is_search_list IS '是否为搜索条件';
COMMENT ON COLUMN efficient_dynamic_forms_fields.remark IS '备注';

DROP TABLE IF EXISTS "efficient_dynamic_forms_data";
CREATE TABLE efficient_dynamic_forms_data  (
                                                 id VARCHAR(64) PRIMARY KEY,
                                                 forms_id VARCHAR(255) NOT NULL,
                                                 org_level_code VARCHAR(255),
                                                 record_data text,
                                                 create_time timestamp,
                                                 create_user varchar(255),
                                                 update_time timestamp,
                                                 update_user varchar(255),
                                                 is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms_data IS '系统动态表单-表单数据';

COMMENT ON COLUMN efficient_dynamic_forms_data.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_data.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_data.org_level_code IS '机构层级';
COMMENT ON COLUMN efficient_dynamic_forms_data.record_data IS '表单数据';

DROP TABLE IF EXISTS "efficient_dynamic_forms_data_detail";
CREATE TABLE efficient_dynamic_forms_data_detail  (
                                                 id VARCHAR(64) PRIMARY KEY,
                                                 forms_id VARCHAR(255) NOT NULL,
                                                 data_id VARCHAR(255) NOT NULL,
                                                 field_id VARCHAR(255) NOT NULL,
                                                 field_value text,
                                                 create_time timestamp,
                                                 create_user varchar(255),
                                                 update_time timestamp,
                                                 update_user varchar(255),
                                                 is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms_data_detail IS '系统动态表单-表单数据-详细';

COMMENT ON COLUMN efficient_dynamic_forms_data_detail.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_data_detail.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_data_detail.field_id IS '字段id';
COMMENT ON COLUMN efficient_dynamic_forms_data_detail.data_id IS '表单数据ID';
COMMENT ON COLUMN efficient_dynamic_forms_data_detail.field_value IS '字段值';

