package com.efficient.openapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/8/26 9:59
 */
@ConfigurationProperties("com.efficient.openapi")
@Data
public class OpenApiProperties {
    /**
     * accessToken 存活时间，分钟，默认五分钟
     */
    private Integer timeToIdle = 5;

}
