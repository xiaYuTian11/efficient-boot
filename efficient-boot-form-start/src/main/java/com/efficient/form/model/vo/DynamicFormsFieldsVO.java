package com.efficient.form.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统动态表单-字段配置 VO
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@ApiModel("系统动态表单-字段配置 返回实体-DynamicFormsFieldsVO")
public class DynamicFormsFieldsVO implements Serializable {

    private static final long serialVersionUID = 3165024814574975073L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
    *表单主键
    */
    @ApiModelProperty(value = "表单主键")
    private String formsId;
    /**
    *字段名称
    */
    @ApiModelProperty(value = "字段名称")
    private String fieldName;
    /**
    *字段类型
    */
    @ApiModelProperty(value = "字段类型")
    private String fieldType;
    /**
    *字段可选值，一般用于下拉
    */
    @ApiModelProperty(value = "字段可选值，一般用于下拉")
    private String fieldOptionValue;
    /**
    *字段默认值
    */
    @ApiModelProperty(value = "字段默认值")
    private String fieldDefaultValue;
    /**
    *字段排序
    */
    @ApiModelProperty(value = "字段排序")
    private Integer sort;
    /**
    *是否必填
    */
    @ApiModelProperty(value = "是否必填")
    private Integer isRequired;
    /**
    *是否在列表上展示
    */
    @ApiModelProperty(value = "是否在列表上展示")
    private Integer isShowList;
    /**
    *展示在列表上的顺序
    */
    @ApiModelProperty(value = "展示在列表上的顺序")
    private Integer showListSort;
    /**
    *是否为搜索条件
    */
    @ApiModelProperty(value = "是否为搜索条件")
    private Integer isSearchList;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String remark;
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
