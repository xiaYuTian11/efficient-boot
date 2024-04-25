package com.efficient.cache.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2024/2/5 10:08
 */
@Configuration
@ConditionalOnProperty(name = "com.efficient.cache.active", havingValue = "redis", matchIfMissing = false)
@Slf4j
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database:0}")
    private int database;
    @Value("${spring.redis.ssl:false}")
    private boolean ssl;
    @Value("${com.efficient.cache.redis.customRedisson:false}")
    private boolean customRedisson;
    @Value("${com.efficient.cache.redis.redissonUrl:null}")
    private String redissonUrl;

    @Bean
    @ConditionalOnProperty(name = "com.efficient.cache.redis.enableRedisson", havingValue = "true", matchIfMissing = false)
    public RedissonClient getRedisson() {
        Config config = new Config();
        try {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig
                    .setSslEnableEndpointIdentification(ssl)
                    .setTimeout(1000 * 5);
            if (customRedisson) {
                singleServerConfig.setAddress(redissonUrl);
            } else {
                singleServerConfig
                        .setAddress("redis://" + redisHost + ":" + port)
                        .setDatabase(database);
                if (StrUtil.isNotBlank(password)) {
                    singleServerConfig.setPassword(password);
                }
            }
            config.setCodec(new JsonJacksonCodec());
        } catch (Exception e) {
            log.error("Create Redisson Error!", e);
        }
        log.info("Create Redisson Success !");
        return Redisson.create(config);
    }
}
