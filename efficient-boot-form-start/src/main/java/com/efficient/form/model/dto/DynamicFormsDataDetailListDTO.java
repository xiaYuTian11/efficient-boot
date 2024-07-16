package com.efficient.form.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
* <p>
* 系统动态表单-表单数据-详细 列表查询DTO
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Data
@ApiModel("系统动态表单-表单数据-详细 列表查询-DynamicFormsDataDetailListDTO")
public class DynamicFormsDataDetailListDTO implements Serializable {
    private static final long serialVersionUID = 7962560633847302361L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
