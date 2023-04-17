package com.efficient.idempotence.config;

import com.efficient.idempotence.properties.IdempotenceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2022/9/5 15:02
 */
@Configuration
@EnableConfigurationProperties(IdempotenceProperties.class)
public class IdempotenceConfig {

    @Autowired
    private IdempotenceProperties idempotenceProperties;

}
