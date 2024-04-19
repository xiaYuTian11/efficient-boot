package com.efficient.logs.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@ApiModel("日志列表请求实体-SysLogListDTO")
public class SysLogListDTO implements Serializable {

    private static final long serialVersionUID = -5096744645564059235L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    /**
     * 系统ID
     */
    @ApiModelProperty("系统标识")
    private String systemId;
    /**
     * 模块
     */
    @ApiModelProperty("模块")
    private String module;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;
    /**
     * 操作IP
     */
    @ApiModelProperty("用户名")
    private String logIp;
    /**
     * 记录日志时间
     */
    @ApiModelProperty("开始时间")
    private Date startDate;
    /**
     * 请求路径
     */
    @ApiModelProperty("结束时间")
    private Date endDate;
    /**
     * 操作类型
     */
    @ApiModelProperty("操作类型")
    private String logOpt;
    /**
     * 返回值
     */
    @ApiModelProperty("是否成功，1-是，0-否")
    private Integer isSuccess;
    @ApiModelProperty("是否成功，1-登录列表，2-操作列表")
    private Integer logType;

}

