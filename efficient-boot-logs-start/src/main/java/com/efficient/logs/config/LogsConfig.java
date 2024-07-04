package com.efficient.logs.config;

import com.efficient.logs.api.LogFunction;
import com.efficient.logs.api.LogFunctionService;
import com.efficient.logs.handle.LogFunctionFactory;
import com.efficient.logs.properties.LogsProperties;
import com.efficient.logs.service.DefaultLogFunctionServiceImpl;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

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

    @Bean
    @Order(1)
    public LogFunctionFactory CustomFunctionRegistrar(@Autowired List<LogFunction> logFunctionList) {
        return new LogFunctionFactory(logFunctionList);
    }

    @Bean
    @Order(2)
    public LogFunctionService customFunctionService(LogFunctionFactory logFunctionFactory) {
        return new DefaultLogFunctionServiceImpl(logFunctionFactory);
    }
}
