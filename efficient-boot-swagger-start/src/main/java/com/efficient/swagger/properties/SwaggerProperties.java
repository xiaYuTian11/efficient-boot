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
    private Boolean enable;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String version;

}
