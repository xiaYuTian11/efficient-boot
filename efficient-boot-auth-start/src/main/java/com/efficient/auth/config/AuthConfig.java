package com.efficient.auth.config;

import com.efficient.auth.properties.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2022/10/28 11:44
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfig {
}
