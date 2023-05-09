package com.efficient.rate.config;

import com.efficient.rate.properties.RateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2022/9/5 15:02
 */
@Configuration
@EnableConfigurationProperties(RateProperties.class)
public class RateConfig {

    @Autowired
    private RateProperties idempotenceProperties;

}
