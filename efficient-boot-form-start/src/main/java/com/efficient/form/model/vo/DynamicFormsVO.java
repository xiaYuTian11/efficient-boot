package com.efficient.form.model.vo;

import com.efficient.form.model.entity.DynamicFormsFields;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>
* 系统动态表单 VO
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@ApiModel("系统动态表单 返回实体-DynamicFormsVO")
public class DynamicFormsVO implements Serializable {

    private static final long serialVersionUID = 8536562396365697747L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
    *表单名称
    */
    @ApiModelProperty(value = "表单名称")
    private String name;
    /**
    *表单描述
    */
    @ApiModelProperty(value = "表单描述")
    private String description;
    /**
    *备注
    */
    @ApiModelProperty(value = "备注")
    private String remark;
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
    *数据权限，1-完全开放，所有人可看，2-只有本人能看，3-按照单位层级查询
    */
    @ApiModelProperty(value = "数据权限，1-完全开放，所有人可看，2-只有本人能看，3-按照单位层级查询")
    private String dataPermissions;
    /**
    *表单样式
    */
    @ApiModelProperty(value = "表单样式")
    private String formStyle;
    /**
    *列表样式
    */
    @ApiModelProperty(value = "列表样式")
    private String listStyle;
    /**
     * 表单字段集合
     */
    @ApiModelProperty(value = "表单字段集合")
    private List<DynamicFormsFields> formsFieldsList;
}
