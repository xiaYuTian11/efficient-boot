package com.efficient.logs.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
* <p>
*  VO
* </p>
*
* @author code generator
* @date 2022-09-05 16:24:37
*/
@Data
@ApiModel(" 返回实体-SysLogVO")
public class SysLogVO {

    private static final long serialVersionUID = 2927798737165925170L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
    *模块
    */
    @ApiModelProperty(value = "模块")
    private String module;
    /**
    *用户ID
    */
    @ApiModelProperty(value = "用户ID")
    private String userId;
    /**
    *用户名
    */
    @ApiModelProperty(value = "用户名")
    private String userName;
    /**
    *操作IP
    */
    @ApiModelProperty(value = "操作IP")
    private String logIp;
    /**
    *记录日志时间
    */
    @ApiModelProperty(value = "记录日志时间")
    private Date logTime;
    /**
    *请求路径
    */
    @ApiModelProperty(value = "请求路径")
    private String requestUrl;
    /**
    *操作类型
    */
    @ApiModelProperty(value = "操作类型")
    private String logOpt;
    /**
    *操作内容
    */
    @ApiModelProperty(value = "操作内容")
    private String logContent;
    /**
    *参数
    */
    @ApiModelProperty(value = "参数")
    private String params;
    /**
    *结果
    */
    @ApiModelProperty(value = "结果")
    private String resultCode;
    /**
    *返回值
    */
    @ApiModelProperty(value = "返回值")
    private String result;
    /**
    *异常信息
    */
    @ApiModelProperty(value = "异常信息")
    private String exception;
}
