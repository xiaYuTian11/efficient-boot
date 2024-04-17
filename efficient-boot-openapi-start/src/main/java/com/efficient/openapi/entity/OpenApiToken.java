package com.efficient.openapi.entity;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/4/16 16:58
 */
@Data
public class OpenApiToken {
    private String userId;
    private Long createTime;
    private String appCode;
}
