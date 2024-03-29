package com.efficient.logs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author code generator
 * @date 2022-09-05 16:24:36
 */
@Data
@TableName("efficient_sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 8313174163871740854L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;
    /**
     * 系统ID
     */
    @TableField("system_id")
    private String systemId;
    /**
     * 模块
     */
    @TableField("module")
    private String module;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;
    /**
     * 用户单位ID
     */
    @TableField("user_unit_id")
    private String userUnitId;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 操作IP
     */
    @TableField("log_ip")
    private String logIp;
    /**
     * 记录日志时间
     */
    @TableField("log_time")
    private Date logTime;
    /**
     * 请求路径
     */
    @TableField("request_url")
    private String requestUrl;
    /**
     * 操作类型
     */
    @TableField("log_opt")
    private String logOpt;
    /**
     * 操作内容
     */
    @TableField("log_content")
    private String logContent;
    /**
     * 参数
     */
    @TableField("params")
    private String params;
    /**
     * 结果
     */
    @TableField("result_code")
    private String resultCode;
    /**
     * 返回值
     */
    @TableField("result")
    private String result;
    /**
     * 异常信息
     */
    @TableField("exception")
    private String exception;
}
