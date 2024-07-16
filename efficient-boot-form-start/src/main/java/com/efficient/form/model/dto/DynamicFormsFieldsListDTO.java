package com.efficient.form.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
* <p>
* 系统动态表单-字段配置 列表查询DTO
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@ApiModel("系统动态表单-字段配置 列表查询-DynamicFormsFieldsListDTO")
public class DynamicFormsFieldsListDTO implements Serializable {
    private static final long serialVersionUID = 9169357157344851612L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
