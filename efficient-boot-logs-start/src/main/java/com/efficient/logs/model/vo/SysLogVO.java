package com.efficient.logs.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * VO
 * </p>
 *
 * @author code generator
 * @date 2022-09-05 16:24:37
 */
@Data
@ApiModel("日志记录列表结果-SysLogVO")
public class SysLogVO implements Serializable {

    private static final long serialVersionUID = 2927798737165925170L;

    /**
     * 主键
     */
    private String id;
    /**
     * 系统ID
     */
    @ApiModelProperty("系统ID")
    private String systemId;
    /**
     * 模块
     */
    @ApiModelProperty("模块")
    private String module;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String userId;
    /**
     * 用户单位ID
     */
    @ApiModelProperty("用户单位ID")
    private String userUnitId;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;
    /**
     * 操作IP
     */
    @ApiModelProperty("操作IP")
    private String logIp;
    /**
     * 记录日志时间
     */
    @ApiModelProperty("记录日志时间")
    private Date logTime;
    /**
     * 操作类型
     */
    @ApiModelProperty("操作类型")
    private String logOpt;
    /**
     * 操作内容
     */
    @ApiModelProperty("操作内容")
    private String logContent;
    /**
     * 结果
     */
    @ApiModelProperty("是否成功，200-成功，其他失败")
    private String resultCode;
}
