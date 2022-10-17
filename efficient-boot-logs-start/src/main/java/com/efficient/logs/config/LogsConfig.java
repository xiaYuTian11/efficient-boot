package com.efficient.logs.config;

import com.efficient.logs.properties.LogsProperties;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(basePackages = {"com.efficient.logs.dao"})
public class LogsConfig {

    @Autowired
    private LogsProperties logsProperties;

}
