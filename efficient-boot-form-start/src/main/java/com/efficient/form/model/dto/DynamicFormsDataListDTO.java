package com.efficient.form.model.dto;

import com.efficient.common.validate.QueryGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 系统动态表单-表单数据 列表查询DTO
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Data
@ApiModel("系统动态表单-表单数据 列表查询-DynamicFormsDataListDTO")
public class DynamicFormsDataListDTO implements Serializable {
    private static final long serialVersionUID = 8036806368382924059L;
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
    @ApiModelProperty(value = "表单ID")
    @NotBlank(groups = QueryGroup.class, message = "formsId 不能为空")
    private String formsId;
    @ApiModelProperty(value = "搜索条件")
    private String keyword;
    @ApiModelProperty(value = "排序字段")
    private String orderField;
    @ApiModelProperty(value = "排序方式,ASC,DESC")
    private String orderType;
    /**
     * 数据库类型
     */
    private String dbType;
}
