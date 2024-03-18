
DROP TABLE IF EXISTS "efficient_sys_notify";
CREATE TABLE efficient_sys_notify (
                                      id VARCHAR(64) PRIMARY KEY,
                                      biz_id VARCHAR(64) not null ,
                                      batch_num VARCHAR(64) not null ,
                                      system_id VARCHAR(10),
                                      menu_id VARCHAR(64),
                                      notify_type VARCHAR(10) not null ,
                                      recipient_system_type VARCHAR(10) not null,
                                      creat_user_id VARCHAR(255) not null,
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
COMMENT ON COLUMN efficient_sys_notify.recipient_system_type IS '接收系统，1-本系统，2-渝快政，9-其他系统';
COMMENT ON COLUMN efficient_sys_notify.creat_user_id IS '创建用户id';
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


