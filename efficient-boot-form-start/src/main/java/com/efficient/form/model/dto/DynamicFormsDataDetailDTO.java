package com.efficient.form.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统动态表单-表单数据-详细 DTO
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@ApiModel("系统动态表单-表单数据-详细 请求实体-DynamicFormsDataDetailDTO")
public class DynamicFormsDataDetailDTO implements Serializable {
    private static final long serialVersionUID = 7279308077052443890L;

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
     *表单数据ID
     */
    @ApiModelProperty(value = "表单数据ID")
    @TableField("data_id")
    private String dataId;
    /**
    *字段id
    */
    @ApiModelProperty(value = "字段id")
    private String fieldId;
    /**
    *字段值
    */
    @ApiModelProperty(value = "字段值")
    private String fieldValue;
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

