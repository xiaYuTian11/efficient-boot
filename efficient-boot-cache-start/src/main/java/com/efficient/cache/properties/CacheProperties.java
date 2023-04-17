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
    private CacheProperties.EhCache ehCache = new CacheProperties.EhCache();
    private CacheProperties.Redis redis = new CacheProperties.Redis();

    @Data
    public static class EhCache {
        private String path = "/ehcache.xml";
    }

    @Data
    public static class Redis {

    }

}
