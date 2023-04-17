package com.efficient.logs.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * DTO
 * </p>
 *
 * @author code generator
 * @date 2022-09-05 16:24:37
 */
@Data
public class SysLogDTO {
    private static final long serialVersionUID = 7197794388449706072L;

    /**
     * 主键
     */
    private String id;
    /**
     * 模块
     */
    private String module;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 操作IP
     */
    private String logIp;
    /**
     * 记录日志时间
     */
    private Date logTime;
    /**
     * 请求路径
     */
    private String requestUrl;
    /**
     * 操作类型
     */
    private String logOpt;
    /**
     * 操作内容
     */
    private String logContent;
    /**
     * 参数
     */
    private String params;
    /**
     * 结果
     */
    private String resultCode;
    /**
     * 返回值
     */
    private String result;
    /**
     * 异常信息
     */
    private String exception;

}

