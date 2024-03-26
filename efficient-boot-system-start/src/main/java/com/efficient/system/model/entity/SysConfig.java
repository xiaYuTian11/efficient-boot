package com.efficient.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统配置 实体类
* </p>
*
* @author TMW
* @date 2024-03-26 10:57:51
*/
@Data
@TableName("efficient_sys_config")
@ApiModel("系统配置")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 7085454771033295288L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private String id;
    /**
    *code
    */
    @ApiModelProperty(value = "code")
    @TableField("code")
    private String code;
    /**
    *配置
    */
    @ApiModelProperty(value = "配置")
    @TableField("config")
    private String config;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;
    /**
    *是否启用
    */
    @ApiModelProperty(value = "是否启用")
    @TableField("is_enable")
    private Integer isEnable;
}
