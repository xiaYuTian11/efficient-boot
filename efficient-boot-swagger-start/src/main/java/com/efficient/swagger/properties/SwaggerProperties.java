package com.efficient.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/8/25 15:46
 */
@ConfigurationProperties("com.efficient.swagger")
@Data
public class SwaggerProperties {
    /**
     * 是否启用
     */
    private Boolean enable = false;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 服务条款网址
     */
    private String termsOfServiceUrl;
    /**
     * 版本号
     */
    private String version = "1.0.0";

}
