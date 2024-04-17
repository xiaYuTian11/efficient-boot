package com.efficient.openapi.entity;

import com.efficient.common.validate.QueryGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author TMW
 * @since 2024/4/16 16:21
 */
@Data
@ApiModel("获取accessToken请求实体-OpenApiTokenRequest")
public class OpenApiAccessTokenRequest {
    @ApiModelProperty(value = "appKey", required = true)
    @NotBlank(groups = QueryGroup.class, message = "appCode 应用名称不能为空")
    private String appCode;
    @ApiModelProperty(value = "appKey", required = true)
    @NotBlank(groups = QueryGroup.class, message = "appKey 应用密钥key不能为空")
    private String appKey;
    @ApiModelProperty(value = "appSecret", required = false)
    private String appSecret;
}
