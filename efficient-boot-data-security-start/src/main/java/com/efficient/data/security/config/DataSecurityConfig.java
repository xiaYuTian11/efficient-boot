package com.efficient.data.security.config;

import com.efficient.data.security.properties.DataSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2023/6/8 17:09
 */
@Configuration
@EnableConfigurationProperties(DataSecurityProperties.class)
public class DataSecurityConfig {

}
