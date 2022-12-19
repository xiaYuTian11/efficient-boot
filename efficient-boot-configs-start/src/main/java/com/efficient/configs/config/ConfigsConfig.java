package com.efficient.configs.config;

import com.efficient.configs.properties.ConfigsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2022/10/17 15:46
 */
@Configuration
@EnableConfigurationProperties(ConfigsProperties.class)
public class ConfigsConfig {

}
