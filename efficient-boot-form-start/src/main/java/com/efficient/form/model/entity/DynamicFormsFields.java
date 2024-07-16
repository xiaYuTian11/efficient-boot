package com.efficient.form.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统动态表单-字段配置 实体类
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@TableName("efficient_dynamic_forms_fields")
@ApiModel("系统动态表单-字段配置")
public class DynamicFormsFields implements Serializable {

    private static final long serialVersionUID = 695890650642468774L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private String id;
    /**
    *表单主键
    */
    @ApiModelProperty(value = "表单主键")
    @TableField("forms_id")
    private String formsId;
    /**
    *字段名称
    */
    @ApiModelProperty(value = "字段名称")
    @TableField("field_name")
    private String fieldName;
    /**
    *字段类型
    */
    @ApiModelProperty(value = "字段类型")
    @TableField("field_type")
    private String fieldType;
    /**
    *字段可选值，一般用于下拉
    */
    @ApiModelProperty(value = "字段可选值，一般用于下拉")
    @TableField("field_option_value")
    private String fieldOptionValue;
    /**
    *字段默认值
    */
    @ApiModelProperty(value = "字段默认值")
    @TableField("field_default_value")
    private String fieldDefaultValue;
    /**
    *字段排序
    */
    @ApiModelProperty(value = "字段排序")
    @TableField("sort")
    private Integer sort;
    /**
    *是否必填
    */
    @ApiModelProperty(value = "是否必填")
    @TableField("is_required")
    private Integer isRequired;
    /**
    *是否在列表上展示
    */
    @ApiModelProperty(value = "是否在列表上展示")
    @TableField("is_show_list")
    private Integer isShowList;
    /**
    *展示在列表上的顺序
    */
    @ApiModelProperty(value = "展示在列表上的顺序")
    @TableField("show_list_sort")
    private Integer showListSort;
    /**
    *是否为搜索条件
    */
    @ApiModelProperty(value = "是否为搜索条件")
    @TableField("is_search_list")
    private Integer isSearchList;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;
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
