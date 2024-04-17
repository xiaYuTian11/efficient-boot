package com.efficient.openapi.config;

import com.efficient.openapi.properties.OpenApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2024/4/16 16:37
 */
@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
@Slf4j
public class OpenApiConfig {

}
