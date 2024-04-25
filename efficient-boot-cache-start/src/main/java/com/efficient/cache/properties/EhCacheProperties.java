package com.efficient.cache.properties;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/4/24 17:52
 */
@Data
public class EhCacheProperties {
    /**
     * 配置文件路由
     */
    private String path = "/ehcache.xml";
}
