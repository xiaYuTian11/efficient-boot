package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息 DTO
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Data
@ApiModel("用户信息 请求实体-SysUserDTO")
public class SysUserDTO implements Serializable {
    private static final long serialVersionUID = 8474019155496516468L;

    /**
     *用户中心 ID
     */
    @ApiModelProperty(value = "用户中心 ID")
    private String id;
    /**
     *姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     *账号
     */
    @ApiModelProperty(value = "账号")
    private String account;
    /**
     *密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     *政务钉Id
     */
    @ApiModelProperty(value = "政务钉Id")
    private String zwddId;
    /**
     *电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;
    /**
     *身份证
     */
    @ApiModelProperty(value = "身份证")
    private String idCard;
    /**
     *是否启用 1-启用，0-停用
     */
    @ApiModelProperty(value = "是否启用 1-启用，0-停用")
    private Integer isEnable;
    /**
     *过期时间
     */
    @ApiModelProperty(value = "过期时间")
    private Date expirationTime;
    /**
     *上次修改密码时间
     */
    @ApiModelProperty(value = "上次修改密码时间")
    private Date updatePasswordTime;
    /**
     *上传登录时间
     */
    @ApiModelProperty(value = "上传登录时间")
    private Date lastLoginTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String createUser;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String updateUser;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer isDelete;
    /**
     *拉取时间
     */
    @ApiModelProperty(value = "拉取时间")
    private Date pullTime;

}

