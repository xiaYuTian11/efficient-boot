package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
* <p>
* 机构数据 列表查询DTO
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Data
@ApiModel("机构数据 列表查询-SysUnitListDTO")
public class SysUnitListDTO implements Serializable {
    private static final long serialVersionUID = 7811755830485170450L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
