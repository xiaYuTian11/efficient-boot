package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统配置 列表查询DTO
 * </p>
 *
 * @author TMW
 * @date 2024-03-26 10:57:51
 */
@Data
@ApiModel("系统配置 列表查询-SysConfigListDTO")
public class SysConfigListDTO implements Serializable {
    private static final long serialVersionUID = 4334108240256415310L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
