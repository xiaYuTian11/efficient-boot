package com.efficient.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统第三方应用 实体类
* </p>
*
* @author TMW
* @date 2024-04-16 16:47:28
*/
@Data
@TableName("efficient_sys_application")
@ApiModel("系统第三方应用")
public class SysApplication implements Serializable {

    private static final long serialVersionUID = 1054813319643809852L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private String id;
    /**
    *系统code
    */
    @ApiModelProperty(value = "系统code")
    @TableField("app_code")
    private String appCode;
    /**
    *系统名称
    */
    @ApiModelProperty(value = "系统名称")
    @TableField("app_name")
    private String appName;
    /**
    *密钥对
    */
    @ApiModelProperty(value = "密钥对")
    @TableField("app_key")
    private String appKey;
    /**
    *密钥对
    */
    @ApiModelProperty(value = "密钥对")
    @TableField("app_secret")
    private String appSecret;
    /**
    *pc端路由
    */
    @ApiModelProperty(value = "pc端路由")
    @TableField("pc_url")
    private String pcUrl;
    /**
    *移动端路由
    */
    @ApiModelProperty(value = "移动端路由")
    @TableField("mobile_url")
    private String mobileUrl;
    /**
    *排序号
    */
    @ApiModelProperty(value = "排序号")
    @TableField("sort")
    private Integer sort;
    /**
    *是否启用，1-是，0-否
    */
    @ApiModelProperty(value = "是否启用，1-是，0-否")
    @TableField("is_enabled")
    private Integer isEnabled;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("update_time")
    private Date updateTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableLogic
    @TableField("is_delete")
    private Integer isDelete;
}
