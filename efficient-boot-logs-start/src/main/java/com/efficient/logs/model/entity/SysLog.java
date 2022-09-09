package com.efficient.logs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
*  实体类
* </p>
*
* @author code generator
* @date 2022-09-05 16:24:36
*/
@Data
@TableName("sys_log")
@ApiModel("")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 8313174163871740854L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    @TableId("id")
    private String id;
    /**
    *模块
    */
    @ApiModelProperty(value = "模块")
    @TableField("module")
    private String module;
    /**
    *用户ID
    */
    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;
    /**
    *用户名
    */
    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;
    /**
    *操作IP
    */
    @ApiModelProperty(value = "操作IP")
    @TableField("log_ip")
    private String logIp;
    /**
    *记录日志时间
    */
    @ApiModelProperty(value = "记录日志时间")
    @TableField("log_time")
    private Date logTime;
    /**
    *请求路径
    */
    @ApiModelProperty(value = "请求路径")
    @TableField("request_url")
    private String requestUrl;
    /**
    *操作类型
    */
    @ApiModelProperty(value = "操作类型")
    @TableField("log_opt")
    private String logOpt;
    /**
    *操作内容
    */
    @ApiModelProperty(value = "操作内容")
    @TableField("log_content")
    private String logContent;
    /**
    *参数
    */
    @ApiModelProperty(value = "参数")
    @TableField("params")
    private String params;
    /**
    *结果
    */
    @ApiModelProperty(value = "结果")
    @TableField("result_code")
    private String resultCode;
    /**
    *返回值
    */
    @ApiModelProperty(value = "返回值")
    @TableField("result")
    private String result;
    /**
    *异常信息
    */
    @ApiModelProperty(value = "异常信息")
    @TableField("exception")
    private String exception;
}
