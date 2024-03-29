package com.efficient.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* efficient_dict_code 实体类
* </p>
*
* @author TMW
* @date 2024-03-29 11:08:17
*/
@Data
@TableName("efficient_dict_code")
@ApiModel("efficient_dict_code")
public class DictCode implements Serializable {

    private static final long serialVersionUID = 6545882351655499319L;

    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("id")
    private Long id;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("code_type")
    private String codeType;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("code")
    private String code;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("code_name")
    private String codeName;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("short_name")
    private String shortName;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("sort")
    private Long sort;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("is_enable")
    private Integer isEnable;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("parent_code")
    private String parentCode;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("code_level")
    private Integer codeLevel;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("is_leaf")
    private Integer isLeaf;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("pin_yin")
    private String pinYin;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("remark")
    private String remark;
}
