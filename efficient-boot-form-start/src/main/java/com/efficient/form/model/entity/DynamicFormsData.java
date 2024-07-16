package com.efficient.form.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统动态表单-表单数据 实体类
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@TableName("efficient_dynamic_forms_data")
@ApiModel("系统动态表单-表单数据")
public class DynamicFormsData implements Serializable {

    private static final long serialVersionUID = 7935591013756155745L;

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
    *机构层级
    */
    @ApiModelProperty(value = "机构层级")
    @TableField("org_level_code")
    private String orgLevelCode;
    /**
    *表单数据
    */
    @ApiModelProperty(value = "表单数据")
    @TableField("record_data")
    private String recordData;
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
