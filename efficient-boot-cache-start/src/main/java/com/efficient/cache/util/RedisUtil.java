package com.efficient.cache.util;

import cn.hutool.core.collection.CollUtil;
import com.efficient.cache.api.CacheUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

/**
 * @author TMW
 * @since 2022/9/2 11:12
 */
public class RedisUtil implements CacheUtil {

    private final static String CONNECTOR_STR = "-";
    private RedisTemplate<String, Object> redisTemplate;

    // @PostConstruct
    // public void postConstruct() {
    //     init(redisTemplate);
    // }
    public void init(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    @Override
    public <T> T get(String cacheName, String key) {
        return (T) redisTemplate.opsForValue().get(cacheName + CONNECTOR_STR + key);
    }

    @Override
    public void put(String cacheName, String key, Object obj) {
        redisTemplate.opsForValue().set(cacheName + CONNECTOR_STR + key, obj);
    }

    @Override
    public void put(String cacheName, String key, Object obj, int timeToIdleSeconds) {
        redisTemplate.opsForValue().set(cacheName + CONNECTOR_STR + key, obj, Duration.ofSeconds(timeToIdleSeconds));
    }

    @Override
    public void refresh(String cacheName, String key, int timeToIdleSeconds) {
        redisTemplate.expire(cacheName + CONNECTOR_STR + key, Duration.ofSeconds(timeToIdleSeconds));
    }

    @Override
    public int getTimeToIdleSeconds(String cacheName, String key) {
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(cacheName + CONNECTOR_STR + key);
        if (Objects.isNull(expire)) {
            return -1;
        }
        return expire.intValue();

    }

    @Override
    public void removeCache(String cacheName, String key) {
        redisTemplate.delete(cacheName + CONNECTOR_STR + key);
    }

    @Override
    public void removeCache(String cacheName) {
        Set<String> keys = redisTemplate.keys(cacheName + "*");
        if (CollUtil.isNotEmpty(keys)) {
            keys.forEach(key -> redisTemplate.delete(key));
        }
    }
}
