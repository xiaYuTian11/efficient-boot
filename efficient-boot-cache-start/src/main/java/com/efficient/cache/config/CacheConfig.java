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
// @EnableCaching
public class CacheConfig {
    @Autowired
    private CacheProperties cacheProperties;

    // @Bean
    // @ConditionalOnProperty(name = "com.efficient.cache.active", havingValue = "redis", matchIfMissing = false)
    // public RedisTemplate<String, Object> redisTemplate() {
    //     RedisTemplate<String, Object> template = new RedisTemplate();
    //     // template.setConnectionFactory(factory);
    //     // Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //     // ObjectMapper om = new ObjectMapper();
    //     // om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //     // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    //     // jackson2JsonRedisSerializer.setObjectMapper(om);
    //     // StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    //     // // key采用String的序列化方式
    //     // template.setKeySerializer(stringRedisSerializer);
    //     // // hash的key也采用String的序列化方式
    //     // template.setHashKeySerializer(stringRedisSerializer);
    //     // // value序列化方式采用jackson
    //     // template.setValueSerializer(jackson2JsonRedisSerializer);
    //     // // hash的value序列化方式采用jackson
    //     // template.setHashValueSerializer(jackson2JsonRedisSerializer);
    //     // template.afterPropertiesSet();
    //     return template;
    // }

    // @Bean
    // @ConditionalOnMissingBean
    // @ConditionalOnProperty(name = "com.efficient.cache.active", havingValue = "redis", matchIfMissing = true)
    // public CacheUtil redis() {
    //     RedisUtil cacheUtil = new RedisUtil();
    //     cacheUtil.init(redisTemplate());
    //     return cacheUtil;
    // }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "com.efficient.cache.active", havingValue = "ehcache", matchIfMissing = true)
    public CacheUtil ehcache() {
        EhCacheUtil cacheUtil = new EhCacheUtil();
        CacheManager cacheManager = new CacheManager(this.getClass().getResource(cacheProperties.getEhCache().getPath()));
        cacheUtil.init(cacheManager);
        return cacheUtil;
    }
}
