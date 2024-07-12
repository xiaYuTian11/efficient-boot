package com.efficient.form.config;

import com.efficient.form.properties.FormProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2024/7/9 11:51
 */
@Configuration
@EnableConfigurationProperties(FormProperties.class)
@MapperScan(basePackages = {"com.efficient.form.dao"})
public class FormConfig {
}
