package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
* <p>
* efficient_dict_code 列表查询DTO
* </p>
*
* @author TMW
* @date 2024-03-29 11:08:17
*/
@Data
@ApiModel("efficient_dict_code 列表查询-DictCodeListDTO")
public class DictCodeListDTO implements Serializable {
    private static final long serialVersionUID = 6316362800367880450L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
