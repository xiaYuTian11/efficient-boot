package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统第三方应用 DTO
* </p>
*
* @author TMW
* @date 2024-04-16 16:47:29
*/
@Data
@ApiModel("系统第三方应用 请求实体-SysApplicationDTO")
public class SysApplicationDTO implements Serializable {
    private static final long serialVersionUID = 210030674791845706L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
    *系统code
    */
    @ApiModelProperty(value = "系统code")
    private String appCode;
    /**
    *系统名称
    */
    @ApiModelProperty(value = "系统名称")
    private String appName;
    /**
    *密钥对
    */
    @ApiModelProperty(value = "密钥对")
    private String appKey;
    /**
    *密钥对
    */
    @ApiModelProperty(value = "密钥对")
    private String appSecret;
    /**
    *pc端路由
    */
    @ApiModelProperty(value = "pc端路由")
    private String pcUrl;
    /**
    *移动端路由
    */
    @ApiModelProperty(value = "移动端路由")
    private String mobileUrl;
    /**
    *排序号
    */
    @ApiModelProperty(value = "排序号")
    private Integer sort;
    /**
    *是否启用，1-是，0-否
    */
    @ApiModelProperty(value = "是否启用，1-是，0-否")
    private Integer isEnabled;
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

}

