package com.efficient.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/9/2 9:14
 */
@ConfigurationProperties("com.efficient.cache")
@Data
public class CacheProperties {
    private String active = "ehcache";
    private EhCacheProperties ehCache = new EhCacheProperties();
    private RedisProperties redis = new RedisProperties();

    // @Data
    // public static class EhCache {
    //     private String path = "/ehcache.xml";
    // }
    //
    // @Data
    // public static class Redis {
    //     /**
    //      * 是否自定义 redisson  url ,默认 false
    //      */
    //     private boolean customRedisson = false;
    //     /**
    //      * 自定义路由
    //      */
    //     private String redissonUrl;
    // }

}
