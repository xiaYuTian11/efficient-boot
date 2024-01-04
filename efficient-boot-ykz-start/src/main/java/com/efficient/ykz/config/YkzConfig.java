package com.efficient.ykz.config;

import com.efficient.ykz.properties.YkzProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2024/1/4 15:27
 */
@Configuration
@EnableConfigurationProperties(YkzProperties.class)
public class YkzConfig {

}
