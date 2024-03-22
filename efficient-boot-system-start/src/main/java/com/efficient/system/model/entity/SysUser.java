package com.efficient.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 用户信息 实体类
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Data
@TableName("efficient_sys_user")
@ApiModel("用户信息")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 2813807927828104438L;

    /**
    *用户中心 ID
    */
    @ApiModelProperty(value = "用户中心 ID")
    @TableId(value = "id")
    private String id;
    /**
    *姓名
    */
    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;
    /**
    *账号
    */
    @ApiModelProperty(value = "账号")
    @TableField("account")
    private String account;
    /**
    *密码
    */
    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;
    /**
    *政务钉Id
    */
    @ApiModelProperty(value = "政务钉Id")
    @TableField("zwdd_id")
    private String zwddId;
    /**
    *电话
    */
    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;
    /**
    *身份证
    */
    @ApiModelProperty(value = "身份证")
    @TableField("id_card")
    private String idCard;
    /**
    *是否启用 1-启用，0-停用
    */
    @ApiModelProperty(value = "是否启用 1-启用，0-停用")
    @TableField("is_enable")
    private Integer isEnable;
    /**
    *过期时间
    */
    @ApiModelProperty(value = "过期时间")
    @TableField("expiration_time")
    private Date expirationTime;
    /**
    *上次修改密码时间
    */
    @ApiModelProperty(value = "上次修改密码时间")
    @TableField("update_password_time")
    private Date updatePasswordTime;
    /**
    *上传登录时间
    */
    @ApiModelProperty(value = "上传登录时间")
    @TableField("last_login_time")
    private Date lastLoginTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("create_time")
    private Date createTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("create_user")
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
    @TableField("update_user")
    private String updateUser;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("is_delete")
    private Integer isDelete;
    /**
    *拉取时间
    */
    @ApiModelProperty(value = "拉取时间")
    @TableField("pull_time")
    private Date pullTime;
}
