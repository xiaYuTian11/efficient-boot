package com.efficient.cache.config;

import com.efficient.cache.api.CacheUtil;
import com.efficient.cache.properties.CacheProperties;
import com.efficient.cache.util.EhCacheUtil;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2022/9/2 10:07
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(name = "com.efficient.cache.active", havingValue = "ehcache", matchIfMissing = true)
public class CacheConfig {
    @Autowired
    private CacheProperties cacheProperties;

    @Bean
    @ConditionalOnMissingBean
    public CacheUtil ehcache() {
        EhCacheUtil cacheUtil = new EhCacheUtil();
        CacheManager cacheManager = new CacheManager(this.getClass().getResource(cacheProperties.getEhCache().getPath()));
        cacheUtil.init(cacheManager);
        return cacheUtil;
    }
}
