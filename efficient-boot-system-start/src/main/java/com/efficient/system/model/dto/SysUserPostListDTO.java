package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 用户职位信息 列表查询DTO
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Data
@ApiModel("用户职位信息 列表查询-SysUserPostListDTO")
public class SysUserPostListDTO implements Serializable {
    private static final long serialVersionUID = 4229836857281393211L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "关键字搜索")
    private String keyword;
}
