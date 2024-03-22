package com.efficient.system.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2024/3/21 14:26
 */
@Configuration
@Slf4j
@MapperScan(basePackages = {"com.efficient.system.dao"})
public class SystemConfig {

}
