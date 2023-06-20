package com.efficient.param.encrypt.config;

import com.efficient.param.encrypt.properties.ParamEncryptProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2023/6/8 17:09
 */
@Configuration
@EnableConfigurationProperties(ParamEncryptProperties.class)
public class ParamEncryptConfig {

}
