DROP TABLE IF EXISTS "efficient_dynamic_forms";
CREATE TABLE efficient_dynamic_forms (
                                         id VARCHAR(64) PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
                                         description text,
                                         remark text,
                                         sort int4 default 1,
                                         is_enabled int2 NOT NULL DEFAULT 1,
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

DROP TABLE IF EXISTS "efficient_dynamic_forms_style";
CREATE TABLE efficient_dynamic_forms_style (
                                               id VARCHAR(64) PRIMARY KEY,
                                               forms_id VARCHAR(255) NOT NULL,
                                               form_style text,
                                               list_style text,
                                               create_time timestamp,
                                               create_user varchar(255),
                                               update_time timestamp,
                                               update_user varchar(255),
                                               is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms_style IS '系统动态表单-前端样式';

COMMENT ON COLUMN efficient_dynamic_forms_style.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_style.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_style.form_style IS '表单样式';
COMMENT ON COLUMN efficient_dynamic_forms_style.list_style IS '列表样式';


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
COMMENT ON COLUMN efficient_dynamic_forms_fields.remark IS '备注';


DROP TABLE IF EXISTS "efficient_dynamic_forms_result";
CREATE TABLE efficient_dynamic_forms_result  (
                                                 id VARCHAR(64) PRIMARY KEY,
                                                 forms_id VARCHAR(255) NOT NULL,
                                                 field_id VARCHAR(255) NOT NULL,
                                                 field_value text,
                                                 create_time timestamp,
                                                 create_user varchar(255),
                                                 update_time timestamp,
                                                 update_user varchar(255),
                                                 is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms_result IS '系统动态表单-表单数据';

COMMENT ON COLUMN efficient_dynamic_forms_result.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_result.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_result.field_id IS '字段id';
COMMENT ON COLUMN efficient_dynamic_forms_result.field_value IS '字段值';

--列表展示字段，搜索条件，排序


DROP TABLE IF EXISTS "efficient_dynamic_forms_list_field";
CREATE TABLE efficient_dynamic_forms_list_field  (
                                               id VARCHAR(64) PRIMARY KEY,
                                               forms_id VARCHAR(255) NOT NULL,
                                               field_id VARCHAR(255) NOT NULL,
                                               field_name VARCHAR(255) NOT NULL,
                                               field_type VARCHAR(255) NOT NULL,
                                               sort int4,
                                               remark text,
                                               create_time timestamp,
                                               create_user varchar(255),
                                               update_time timestamp,
                                               update_user varchar(255),
                                               is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms_list_field IS '系统动态表单-表单列表展示字段';

COMMENT ON COLUMN efficient_dynamic_forms_list_field.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_list_field.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_list_field.field_id IS '字段id';
COMMENT ON COLUMN efficient_dynamic_forms_list_field.field_name IS '字段名称';
COMMENT ON COLUMN efficient_dynamic_forms_list_field.field_type IS '字段类型';
COMMENT ON COLUMN efficient_dynamic_forms_list_field.sort IS '字段排序';
COMMENT ON COLUMN efficient_dynamic_forms_list_field.remark IS '备注';

DROP TABLE IF EXISTS "efficient_dynamic_forms_list_search";
CREATE TABLE efficient_dynamic_forms_list_search  (
                                                     id VARCHAR(64) PRIMARY KEY,
                                                     forms_id VARCHAR(255) NOT NULL,
                                                     field_id VARCHAR(255) NOT NULL,
                                                     field_name VARCHAR(255) NOT NULL,
                                                     field_type VARCHAR(255) NOT NULL,
                                                     sort int4,
                                                     create_time timestamp,
                                                     create_user varchar(255),
                                                     update_time timestamp,
                                                     update_user varchar(255),
                                                     is_delete int2 default 0
);

COMMENT ON TABLE efficient_dynamic_forms_list_search IS '系统动态表单-表单列表搜索字段';

COMMENT ON COLUMN efficient_dynamic_forms_list_search.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_list_search.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_list_search.field_id IS '字段id';
COMMENT ON COLUMN efficient_dynamic_forms_list_search.field_name IS '字段名称';
COMMENT ON COLUMN efficient_dynamic_forms_list_search.field_type IS '字段类型';
COMMENT ON COLUMN efficient_dynamic_forms_list_search.sort IS '字段排序';

DROP TABLE IF EXISTS "efficient_dynamic_forms_list_order";
CREATE TABLE efficient_dynamic_forms_list_order  (
                                                      id VARCHAR(64) PRIMARY KEY,
                                                      forms_id VARCHAR(255) NOT NULL,
                                                      field_id VARCHAR(255) NOT NULL,
                                                      order_type VARCHAR(255) NOT NULL,
                                                      sort int4,
                                                      create_time timestamp,
                                                      create_user varchar(255),
                                                      update_time timestamp,
                                                      update_user varchar(255),
                                                      is_delete int2 default 0

);

COMMENT ON TABLE efficient_dynamic_forms_list_order IS '系统动态表单-表单列表排序';

COMMENT ON COLUMN efficient_dynamic_forms_list_order.id IS '主键';
COMMENT ON COLUMN efficient_dynamic_forms_list_order.forms_id IS '表单主键';
COMMENT ON COLUMN efficient_dynamic_forms_list_order.field_id IS '字段id';
COMMENT ON COLUMN efficient_dynamic_forms_list_order.order_type IS '排序类型，字典表（DYNAMIC_FORMS_LIST_ORDER_TYPE），1-正序，2倒序';
COMMENT ON COLUMN efficient_dynamic_forms_list_order.sort IS '字段排序';

