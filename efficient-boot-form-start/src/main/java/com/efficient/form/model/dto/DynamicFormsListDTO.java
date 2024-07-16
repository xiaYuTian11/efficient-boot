package com.efficient.form.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
* <p>
* 系统动态表单 列表查询DTO
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@ApiModel("系统动态表单 列表查询-DynamicFormsListDTO")
public class DynamicFormsListDTO implements Serializable {
    private static final long serialVersionUID = 1507040346549341851L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "名称")
    private Integer isEnabled;
}
