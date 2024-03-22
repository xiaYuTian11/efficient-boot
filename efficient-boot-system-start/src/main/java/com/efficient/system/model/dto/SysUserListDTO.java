package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
* <p>
* 用户信息 列表查询DTO
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Data
@ApiModel("用户信息 列表查询-SysUserListDTO")
public class SysUserListDTO implements Serializable {
    private static final long serialVersionUID = 1173946989832155210L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
