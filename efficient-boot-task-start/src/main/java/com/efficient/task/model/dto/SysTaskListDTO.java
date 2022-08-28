package com.efficient.task.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* <p>
* 定时任务表 列表查询DTO
* </p>
*
* @author code generator
* @date 2022-08-28 18:08:04
*/
@Data
@ApiModel("定时任务表 列表查询-SysTaskListDTO")
public class SysTaskListDTO {
    private static final long serialVersionUID = 3300146638987479977L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
}
