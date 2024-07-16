package com.efficient.form.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统动态表单 实体类
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@TableName("efficient_dynamic_forms")
@ApiModel("系统动态表单")
public class DynamicForms implements Serializable {

    private static final long serialVersionUID = 6124956303905109251L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private String id;
    /**
    *表单名称
    */
    @ApiModelProperty(value = "表单名称")
    @TableField("name")
    private String name;
    /**
    *表单描述
    */
    @ApiModelProperty(value = "表单描述")
    @TableField("description")
    private String description;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;
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
    *数据权限，1-完全开放，所有人可看，2-只有本人能看，3-按照单位层级查询
    */
    @ApiModelProperty(value = "数据权限，1-完全开放，所有人可看，2-只有本人能看，3-按照单位层级查询")
    @TableField("data_permissions")
    private String dataPermissions;
    /**
    *表单样式
    */
    @ApiModelProperty(value = "表单样式")
    @TableField("form_style")
    private String formStyle;
    /**
    *列表样式
    */
    @ApiModelProperty(value = "列表样式")
    @TableField("list_style")
    private String listStyle;
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
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
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
