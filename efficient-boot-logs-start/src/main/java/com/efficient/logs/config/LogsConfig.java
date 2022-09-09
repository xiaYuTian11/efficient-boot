package com.efficient.logs.config;

import com.efficient.logs.properties.LogsProperties;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2022/9/5 15:02
 */
@Aspect
@Configuration
@EnableConfigurationProperties(LogsProperties.class)
public class LogsConfig {

    @Autowired
    private LogsProperties logsProperties;

}
