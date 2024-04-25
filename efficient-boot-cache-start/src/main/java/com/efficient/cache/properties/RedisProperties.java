package com.efficient.cache.properties;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/4/24 17:52
 */
@Data
public class RedisProperties {
    /**
     * 启用 Redisson
     */
    private boolean enableRedisson = false;
    /**
     * 是否自定义 redisson  url ,默认 false
     */
    private boolean customRedisson = false;
    /**
     * 自定义路由
     */
    private String redissonUrl = null;
}
